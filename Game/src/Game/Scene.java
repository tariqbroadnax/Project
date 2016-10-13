package Game;

import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;

import DataStructure.Tile;
import EntityComponent.GraphicsComponent;
import EntityManager.EntityManager;
import Graphic.GraphicsContext;
import Graphic.Paintable;
import Graphic.TileMap2;

public class Scene 
		implements Updatable, Paintable, Serializable
{
	private Collection<EntityManager> managers;
		
	private HashSet<Entity> entities;
	
	private TileMap2 tileMap;
	
	private LightMap lightMap;

	//private SceneResources resources;
	
	//private TileMapGraphic mapG;
	
	//private Queue<Entity> removeQueue2;
	
	public Scene()
	{			
		managers = new LinkedList<EntityManager>();
		
		entities = new HashSet<Entity>();
		
		tileMap = new TileMap2();

		lightMap = new LightMap();
		
		addManagers();
		
		//addEntity(new NPC());		
	}

	@Override
	public void update(Duration delta)
	{
		//addAndRemoveEntitiesFromQueue();
		
		//updateTileMap();
		updateEntities(delta);	
		updateManagers(delta);
		
	}
	
	@Override
	public void paint(GraphicsContext gc)
	{
		tileMap.paint(gc);
		paintEntities(gc);
		
		//lightMap.paint(gc);
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
	
	protected void updateTileMap()
	{
		resources.tileMap.clear();
		
		for(Entity e : resources.entities)
		{
			Rectangle2D.Double bound = 
					e.get(GraphicsComponent.class)
					 .getGraphic()
					 .getBound();
			
			for(Tile tile : resources.tileMap.tilesOfBound(bound))
				resources.tileMap.addToTile(e, tile);
		}
	}
	
	public void addEntity(Entity e)
	{
		entities.add(e);
	}
	
	private void _addEntity(Entity e)
	{
		resources.entities.add(e);
		
		resources.componentMap.addEntity(e);
	}
	
	private void addAndRemoveEntitiesFromQueue()
	{
		while(!resources.addQueue.isEmpty())
			_addEntity(resources.addQueue.poll());
		
		while(!removeQueue2.isEmpty())
			_removeEntity(removeQueue2.poll());
		
		while(!resources.removeQueue.isEmpty())
			removeQueue2.add(resources.removeQueue.poll());
		
	}
	
	public void removeEntity(Entity e)
	{
		resources.removeQueue.add(e);
	}
	
	public void _removeEntity(Entity e)
	{
		resources.entities.remove(e);
		
		resources.componentMap.removeEntity(e);
	}

	private void addManagers()
	{
		/*CollectionUtilities.addAll(managers,
				new CollisionManager(resources),
				//new EntitySpawner(resources),
				new EntityDisposer(resources),
				new BehaviourManager(resources));
	*/}
	
	public TileMap2 getTileMap()
	{
		return tileMap;
	}
	
	private void readObject(ObjectInputStream in)
		throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
	}
}
