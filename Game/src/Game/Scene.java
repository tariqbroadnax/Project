package Game;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import Entity.Entity;
import EntityComponent.EntityComponent;
import EntityComponent.GraphicsComponent;
import EntityComponent.LightComponent;
import EntityManager.CollisionManager;
import Graphic.Camera;
import Graphic.Graphic;
import Graphic.GraphicsContext;
import Graphic.LayeredGraphic;
import Graphic.Paintable;
import Maths.Dimension2D;
import Tileset.TileMap;
import Weather.RainManager;

public class Scene extends Observable
		implements Updatable, Paintable, Serializable,
				   Observer
{	
	private Map<Class<? extends EntityComponent>, List<Entity>> compMap;
		
	private Set<Entity> addQ, removeQ;
	private Set<Entity> entities;
		
	private LightMap lightMap;
	private TerrainMap terrMap;
	
	private GClock clock;
	
	private boolean gridVisible;
	
	private RainManager rain;

	private LayeredGraphic background;
	
	private CollisionManager collManager;
	
	private TileMap tileMap;
	
	private ScenePainter painter;
	
	private List<Graphic> graphics;
	
	public Scene()
	{			
		compMap = new HashMap<Class<? extends EntityComponent>,
					          List<Entity>>();
		
		addQ = new HashSet<Entity>();
		removeQ = new HashSet<Entity>();
		entities = new HashSet<Entity>();
		
		lightMap = new LightMap();
	
		clock = new GClock();
		
		background = new LayeredGraphic();
		
		tileMap = new TileMap();
		terrMap = new TerrainMap(this);
		
		gridVisible = true;
	
		rain = new RainManager();
		
		collManager = new CollisionManager(this);
		
		painter = new ScenePainter(this);
		
		graphics = new ArrayList<Graphic>();
		
		background.add(tileMap);
		background.add(new TileMap());
		
		addManagers();
	}

	@Override
	public void update(Duration delta)
	{
		//addAndRemoveEntitiesFromQueue();
		
		//updateTileMap();
		
		clearAddQueue();
		clearRemoveQueue();
		clock.update(delta);
		//rain.update(delta);
		updateEntities(delta);	
		
		collManager.update(delta);
	}
	
	@Override
	public void paint(GraphicsContext gc)
	{
		//tileMap.paint(gc)
		//lightMap.paint(gc);
		
		//tileMap.paint(gc);
		
		drawGrid(gc);
		painter.paint(gc);
		
		//rain.paint(gc);
	}
	
	private void drawGrid(GraphicsContext gc)
	{	
		Dimension2D.Double dim = 
				gc.camera.sizeOnScreen(20, 20);
		
		Rectangle2D.Double viewBound = 
				gc.camera.viewBound();
		
		int minX = (int)(viewBound.x / dim.width) - 1,
			minY = (int)(viewBound.y / dim.height) - 1,
			maxX = (int)((viewBound.x + viewBound.width) / dim.width) + 1,
			maxY = (int)((viewBound.y + viewBound.height) / dim.height) + 1;
		
		Rectangle2D.Double rect = 
				new Rectangle2D.Double(
						0, 0, dim.width, dim.height);
		
		gc.g2d.setColor(Color.black);
		for(int y = minY; y < maxY; y++)
		{			
			rect.y = y * dim.height;
			for(int x = minX; x < maxX; x++)
			{
				rect.x = x * dim.width;
				gc.g2d.draw(rect);
				gc.g2d.drawString(x * 20 + " " + y * 20,
								  (int)rect.x,(int)rect.y);
			}
		}
	}
	
	protected void updateEntities(Duration delta)
	{
		for(Entity e : entities)
			e.update(delta);
	}
	
	public void paintEntities(GraphicsContext gc)
	{	
		entities.stream()
				.filter(e -> e.contains(GraphicsComponent.class))
				.map(e -> e.get(GraphicsComponent.class))
				.forEach(comp -> comp.paint(gc));
	}
	
	public List<Entity> entitiesVisibleAtLocation(
			Point2D.Double loc)
	{
		List<Entity> visibleEntities =
				new LinkedList<Entity>();
		
		for(Entity entity : entities)
		{
			if(entity.contains(GraphicsComponent.class))
			{
				Rectangle2D.Double graphicBound =
						entity.get(GraphicsComponent.class)
							  .getGraphic()
							  .getBound();
				
				if(graphicBound.contains(loc))
					visibleEntities.add(entity);
			}
		}
		
		return visibleEntities;
	}
	
	public List<Graphic> graphicsAtLocation(Point2D.Double loc)
	{
		List<Graphic> visibleGraphics = new ArrayList<Graphic>();
		
		for(Graphic graph : graphics)
		{
			Rectangle2D.Double bound = graph.getBound();
			
			if(bound.contains(loc))
				visibleGraphics.add(graph);
		}
		
		return visibleGraphics;
	}
	
	public List<Entity> entitiesVisibleInsideCamera(Camera camera)
	{
		List<Entity> visibleEntities =
				new LinkedList<Entity>();
				
		for(Entity entity : entities)
		{
			if(entity.contains(GraphicsComponent.class))
			{
				Rectangle2D.Double graphicBound =
						entity.get(GraphicsComponent.class)
							  .getGraphic()
							  .getBound();
				
				if(camera.shows(graphicBound))
					visibleEntities.add(entity);
			}
		}
		
		return visibleEntities;
	}
	
	public List<Entity> entitiesWithComponent(
			Class<? extends EntityComponent> c)
	{
		List<Entity> entities = compMap.get(c);
		
		if(entities == null)
			return new LinkedList<Entity>();
		else
			return Collections.unmodifiableList(entities);
	}
	
	public void addGraphic(Graphic graph)
	{
		graphics.add(graph);
	}
	
	public void addEntity(Entity entity)
	{
		if(entity == null)
			throw new NullPointerException();
		
		entity.setSceneLoc(this);
		entity.addObserver(this);
		
		addQ.add(entity);

		setChanged();
		notifyObservers();
	}
	
	private void clearAddQueue()
	{
		for(Entity e : addQ)
		{
			if(e.contains(LightComponent.class))
				lightMap.add(e.get(LightComponent.class)
							  .getLight());	
			entities.add(e);
			
			for(Class<? extends EntityComponent> c :
				e.getComponentClasses())
			{
				if(compMap.get(c) == null)
					compMap.put(c, new LinkedList<Entity>());
				
				compMap.get(c).add(e);
			}
		}
		
		addQ.clear();
	}
	
	private void clearRemoveQueue()
	{
		for(Entity e : removeQ)
		{
			if(e.contains(LightComponent.class))
				lightMap.remove(e.get(LightComponent.class)
							  .getLight());	
			entities.remove(e);
			
			for(Class<? extends EntityComponent> c :
				e.getComponentClasses())
				compMap.get(c).remove(e);
		}
		
		removeQ.clear();
	}
	
	public void removeEntity(Entity entity)
	{
		if(removeQ.add(entity))
		{
			entity.deleteObserver(this);
			entity.setSceneLoc(null);
		}
		
		setChanged();
		notifyObservers();
	}
	
	public boolean contains(Entity entity)
	{
		return entities.contains(entity);
	}
	
	public Collection<Entity> getEntities()
	{
		return Collections.unmodifiableCollection(entities);
	}
	
	public List<Entity> getEntities(Class<? extends EntityComponent> c)
	{
		if(compMap.containsKey(GraphicsComponent.class))
			return compMap.get(c);
		else
			return new LinkedList<Entity>();
	}
	
	
	private void addManagers()
	{
		//CollectionUtilities.addAll(managers,
				//new CollisionManager(resources));
				//new EntitySpawner(resources),
				//new EntityDisposer(resources),
				//new BehaviourManager(resources));
	
	}
	
	public void setGridVisible(boolean gridVisible)
	{
		this.gridVisible = gridVisible;
	}
	
	public void addToBackground(TileMap map)
	{
		background.add(map);
	}
	
	public Graphic getBackgroundLayer(int layer) 
	{
		return background.getLayer(layer);
	}
	
	public int getBackgroundLayerCount() 
	{
		return background.size();
	}
	
	public LightMap getLightMap() {
		return lightMap;
	}
	
	public TileMap getTileMap() {
		return tileMap;
	}
	
	public List<Graphic> getGraphics() {
		return graphics;
	}
	
	public CollisionManager getCollisionManager() {
		return collManager;
	}
	
	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
	}

	@Override
	public void update(Observable o, Object src) 
	{
		setChanged();
		notifyObservers();
	}
}
