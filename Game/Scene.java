package Game;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Set;

import EntityComponent.GraphicsComponent;
import EntityComponent.LightComponent;
import EntityManager.EntityManager;
import Graphic.Camera;
import Graphic.GraphicsContext;
import Graphic.Paintable;
import Graphic.TileMap2;
import Maths.Dimension2D;
import Weather.RainManager;

public class Scene extends Observable
		implements Updatable, Paintable, Serializable,
				   Observer
{
	private Collection<EntityManager> managers;
		
	private Set<Entity> addQ;
	private Set<Entity> entities;
		
	private TileMap2 tileMap;
	
	private LightMap lightMap;
	
	private GClock clock;
	
	private boolean gridVisible;
	
	private RainManager rain;
	
	/* stores entity in layers
	 * stores layer backgrounds 
	 */
	
	//private SceneResources resources;
	
	//private TileMapGraphic mapG;
	
	//private Queue<Entity> removeQueue2;
	
	public Scene()
	{			
		managers = new LinkedList<EntityManager>();
		
		addQ = new HashSet<Entity>();
		entities = new HashSet<Entity>();
		
		lightMap = new LightMap();
	
		clock = new GClock();
		
		tileMap = new TileMap2();
				
		gridVisible = true;
	
		rain = new RainManager();
		
		addManagers();
	}

	@Override
	public void update(Duration delta)
	{
		//addAndRemoveEntitiesFromQueue();
		
		//updateTileMap();
		for(Entity e : addQ)
		{
			if(e.contains(LightComponent.class))
				lightMap.add(e.get(LightComponent.class)
							  .getLight());	
			entities.add(e);
		}
		addQ.clear();
		
		clock.update(delta);
		//rain.update(delta);
		updateEntities(delta);	
		updateManagers(delta);	
	}
	
	@Override
	public void paint(GraphicsContext gc)
	{
		//tileMap.paint(gc)

		drawGrid(gc);

		entities.stream()
				.map(e -> e.get(GraphicsComponent.class))
				.filter(comp -> comp != null)
				.forEach(g -> g.paint(gc));	

		lightMap.paint(gc);

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
			e.updateComponents(delta);
	}
	
	protected void updateManagers(Duration delta)
	{
		for(EntityManager manager : managers)
			manager.update(delta);
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
	
	public void addEntity(Entity entity)
	{
		if(entity == null)
			throw new NullPointerException();
		
		addQ.add(entity);
		
		entity.setSceneLoc(this);
		
		entity.addObserver(this);
		
		setChanged();
		notifyObservers();
	}
	
	public void removeEntity(Entity entity)
	{
		if(entities.remove(entity))
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
	
	private void addManagers()
	{
		/*CollectionUtilities.addAll(managers,
				new CollisionManager(resources),
				//new EntitySpawner(resources),
				new EntityDisposer(resources),
				new BehaviourManager(resources));
	*/
	}
	
	public void setGridVisible(boolean gridVisible)
	{
		this.gridVisible = gridVisible;
	}
	
	public TileMap2 getTileMap() {
		return tileMap;
	}
	
	public LightMap getLightMap() {
		return lightMap;
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
