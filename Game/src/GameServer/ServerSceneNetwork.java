package GameServer;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import Game.Entity;
import Game.Player;

public class ServerSceneNetwork
{
	private Collection<Entity> addQ, removeQ;
	
	private HashMap<Integer, ServerScene> network;
	
	public ServerSceneNetwork()
	{
		network = new HashMap<Integer, ServerScene>();
		
		addQ = Collections.synchronizedCollection(new LinkedList<Entity>());
		removeQ = Collections.synchronizedCollection(new LinkedList<Entity>());
	}
	
	public void updateScenes(Duration delta)
	{
		for(ServerScene scene : network.values())
		{
			scene.update(delta);
			moveEntitiesToProperScene(scene);
		}
		
		clearAddQueue();
		clearRemoveQueue();
	}
	
	private void moveEntitiesToProperScene(ServerScene scene)
	{
		for(Entity movedEntity : scene.getMovedEntities())
			addEntityToScene(movedEntity);
	}
	
	private void clearAddQueue()
	{
		synchronized(addQ)
		{
			Iterator<Entity> esIter = addQ.iterator();
			
			while(esIter.hasNext())
				addEntityToScene(esIter.next());
		
			addQ.clear();
		}
	}
	
	private void clearRemoveQueue()
	{
		synchronized(removeQ)
		{
			Iterator<Entity> esIter = removeQ.iterator();
			
			while(esIter.hasNext())
				removeEntityFromScene(esIter.next());
			
			removeQ.clear();
		}
	}
	
	public void addEntity(Entity e)
	{		
		addQ.add(e);	
	}
	
	private void addEntityToScene(Entity e)
	{
		int sceneID = e.getLocationSceneID();

		if(!network.containsKey(sceneID))
			createScene(sceneID);
			
		network.get(sceneID)
			   .addEntity(e);
	}
	
	public void removeEntity(Entity e)
	{
		removeQ.add(e);
	}
	
	private void removeEntityFromScene(Entity e)
	{
		int sceneID = e.getLocationSceneID();
		
		ServerScene scene = network.get(sceneID);
		
		scene.removeEntity(e);
		
		if(scene.getPlayers().size() == 0)
			network.remove(sceneID);
	}
	
	private void createScene(int sceneID)
	{
		network.put(sceneID, new ServerScene());
	}
	
	public ServerScene getScene(int sceneID)
	{
		return network.get(sceneID);
	}
	
	public ServerScene getSceneOf(Player player)
	{
		return getScene(player.getLocationSceneID());
	}
	
	public Collection<ServerScene> getLoadedScenes()
	{
		return Collections.unmodifiableCollection(network.values());
	}
}
