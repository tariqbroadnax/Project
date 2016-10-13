package GameServer;

import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.TreeSet;

import DataStructure.Tile;
import DebugGUI.EntityViewerFrame;
import EntityComponent.GraphicsComponent;
import EntityManager.EntityManager;
import Game.Entity;
import Game.Player;
import Game.SceneResources;
import Game.Updatable;
import Graphic.GraphicsContext;

public class ServerScene implements Updatable, Serializable
{
	private Collection<EntityManager> managers;
	
	private SceneResources resources;
	
	private int nextID = 0;
	
	private int sceneID = 0;
	
	private Collection<Player> players;
	
	private Collection<Entity> movedEntities;
	
	private Collection<Entity> entities;
	
	private ChatLog chatLog;
	
	public ServerScene()
	{
		resources = new SceneResources();
		
		managers = new LinkedList<EntityManager>();
	
		players = new LinkedList<Player>();
		
		movedEntities = new LinkedList<Entity>();
		
		entities = new TreeSet<Entity>();
		
		chatLog = new ChatLog();
		
		addManagers();
	}

	@Override
	public void update(Duration delta)
	{				
		addAndRemoveEntities();
		clearAddAndRemoveQueues();
		movedEntities.clear();
		
		updateTileMap();
		updateEntities(delta);	
		updateManagers(delta);			
	}
	
	protected void updateEntities(Duration delta)
	{
		for(Entity e : resources.entities)
		{
			e.update(delta);
			
			if(e.getLocationSceneID() != sceneID)
			{
				movedEntities.add(e);
				removeEntity(e);
			}
		}
	}
	
	protected void updateManagers(Duration delta)
	{
		for(EntityManager manager : managers)
			manager.update(delta);
	}
	
	public void paintEntities(GraphicsContext gc)
	{
		Collection<Entity> es = 
				resources.componentMap
						 .getEntitiesWithComponent(
								 GraphicsComponent.class);
		
		for(Entity e : es)
			e.get(GraphicsComponent.class)
			 .paint(gc);
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
			
			if(bound == null)
				continue;
			
			for(Tile tile : resources.tileMap.tilesOfBound(bound))
				resources.tileMap.addToTile(e, tile);
		}
	}
	
	public void addEntity(Entity e)
	{
		resources.addQueue.add(e);
		entities.add(e);
		e.setEntityID(nextID++);	
	}
	
	private void addAndRemoveEntities()
	{
		for(Entity e : resources.addQueue)
			_addEntity(e);
		
		for(Entity e : resources.removeQueue)
			_removeEntity(e);
	}
	
	private void clearAddAndRemoveQueues()
	{
		resources.addQueue.clear();
		resources.removeQueue.clear();
	}
	
	private void _addEntity(Entity e)
	{
		int i = Collections.binarySearch(
			resources.entities, e);
				
		if(i < 0)
			resources.entities.add(-i-1, e);
		else
			resources.entities.add(i, e);
			
		resources.componentMap.addEntity(e);

		if(e instanceof Player)
		{
			new EntityViewerFrame(e);
			players.add((Player)e);		
		}
	}
	
	public void removeEntity(Entity e)
	{
		resources.removeQueue.add(e);
	}
	
	public void _removeEntity(Entity e)
	{
		resources.entities.remove(e);
		
		resources.componentMap.removeEntity(e);
		
		entities.remove(e);
		
		if(e instanceof Player)
			players.remove((Player)e);
	}
	
	public Collection<Player> getPlayers()
	{
		return Collections.unmodifiableCollection(players);
	}

	public Collection<Entity> getEntities()
	{
		return Collections.unmodifiableCollection(entities);
	}
	
	public Collection<Entity> getMovedEntities()
	{
		return Collections.unmodifiableCollection(
				movedEntities);
	}
	
	public Collection<Entity> getAddedEntities()
	{
		return Collections.unmodifiableCollection(
				resources.addQueue);
	}
	
	public Collection<Entity> getRemovedEntities()
	{
		return Collections.unmodifiableCollection(
				resources.removeQueue);
	}
	
	private void addManagers()
	{
		//CollectionUtilities.addAll(managers,
				//new CollisionManager(resources),
				//new EntitySpawner(resources),
				//new EntityDisposer(resources),
				//new BehaviourManager(resources));
	}
	
	public SceneResources getResources()
	{
		return resources;
	}
	
	public int getSceneID()
	{
		return sceneID;
	}
	
	public ChatLog getChatLog()
	{
		return chatLog;
	}
}
