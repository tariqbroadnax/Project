package GameServer;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

import Game.Entity;
import Game.Player;
import Game.Updater;
import GameClient.ClientCommand;

public class ServerUpdater extends Updater
{
	private static final int UPDATE_RATE = 60;
	
	private ServerScene scene;
	
	private GameServerNetwork network;
	
	private ServerSceneNetwork sceneNetwork;
	
	private int updatesUntilNoPlayerCompression = 0;
	
	public ServerUpdater(
			GameServerNetwork network,
			ServerSceneNetwork sceneNetwork)
	{
		super(UPDATE_RATE);
		
		this.network = network;
		
		this.sceneNetwork = sceneNetwork;
		
		addUpdatable(
				delta -> invokeClientRequest(),
				delta -> updateScenes(delta),
				delta -> sendInitialUpdates(),
				delta -> updateClients());
	}

	private void invokeClientRequest()
	{
		Collection<ClientConnection> connections =
				network.getClientConnector()
					   .getConnections();
		
		for(ClientConnection conn : connections)
		{
			PlayerRecord record = network.getPlayerRecord(conn);
			
			Player player = record.getPlayer();
		
			Collection<ClientCommand> commands =
					network.getClientCommandBuffer()
						   .getRequestOf(conn);
			
			for(ClientCommand request : commands)
				request.execute(player, sceneNetwork);
		}
		
		
		network.getClientCommandBuffer()
			   .flush();
	}
	
	private void updateScenes(Duration delta)
	{
		sceneNetwork.updateScenes(delta);
	}
	
	private void sendInitialUpdates()
	{
		Collection<ClientConnection> newConns =
				network.getNewConnections();
		
		synchronized(newConns)
		{
			for(ClientConnection conn : newConns)
			{
				Player player = network.getPlayerRecord(conn)
			   			 			   .getPlayer();
				
				int entityID = player.getEntityID();
				int sceneID = player.getLocationSceneID();
				
				ServerScene scene = sceneNetwork.getScene(sceneID);
	
				Collection<Entity> updatedEntities = 
						clonedEntities(scene.getEntities());
				
				Collection<ChatMessage> messages =
						scene.getChatLog()
							 .getMessages();
				
				updatedEntities.stream()
							   .filter(e -> !(e instanceof Player))
							   .forEach(e -> e.compress());				
				
				ServerUpdate update = new ServerUpdate(updatedEntities, null, messages);
																		
				try {
					conn.getObjectOutputStream()
						.writeObject(entityID);
					conn.getObjectOutputStream()
						.writeObject(update);
					
					conn.getObjectOutputStream()
						.reset();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
			newConns.clear();
		}
	}
	
	private Collection<Entity> clonedEntities(Collection<Entity> es)
	{
		Collection<Entity> entities = 
				new LinkedList<Entity>();
		
		for(Entity e : es)
			entities.add((Entity)e.clone());
		
		return entities;
	}
	
	private void compressEntities(Collection<Entity> es)
	{				
		if(updatesUntilNoPlayerCompression != 0)
			es.forEach(e -> e.compress());
		else
		{
			es.stream()
			  .filter(e -> !(e instanceof Player))
			  .forEach(e -> e.compress());
		}
		
	}
	
	private HashMap<Player, ServerUpdate> createUpdates()
	{		
		HashMap<Player, ServerUpdate> updates =
				new HashMap<Player, ServerUpdate>();
				
		for(ServerScene scene : sceneNetwork.getLoadedScenes())
		{
			Collection<Entity> updatedEntities = 
									clonedEntities(scene.getEntities()),
							   removedEntities = 
							   		clonedEntities(scene.getRemovedEntities());
			
			compressEntities(updatedEntities);
			compressEntities(removedEntities);

			//if(addedEntities.size() > 0)
				//System.out.println("added entities:" + addedEntities.size());
			
			ServerUpdate update;
						
			for(Player player : scene.getPlayers())
			{
				update = new ServerUpdate(
						updatedEntities,
						removedEntities,
						scene.getChatLog()
							 .getNewMessages()
							 .stream()
							 .filter(mess -> !mess.getPlayerName().equals(player.getName()))
							 .collect(Collectors.toList()));
				
				updates.put(player, update);
			}
			
			scene.getChatLog()
				 .flushNewMessages();
		}
		
		updatesUntilNoPlayerCompression = 
				updatesUntilNoPlayerCompression == 0 ?
				UPDATE_RATE : updatesUntilNoPlayerCompression;
				
		updatesUntilNoPlayerCompression--;
		
		return updates;
	}
	
	private void sendUpdates(HashMap<Player, ServerUpdate> updates)
	{
		Collection<ClientConnection> connections =
				network.getClientConnector()
					   .getConnections();
				
		synchronized(connections)
		{
			Iterator<ClientConnection> connIter =
					connections.iterator();
			
			while(connIter.hasNext())
			{
				ClientConnection conn = connIter.next();
				
				Player player = 
						network
						.getPlayerRecord(conn)
						.getPlayer();
				
				ServerUpdate update =
						updates.get(player);
			
				if(update == null) continue;
				
				ObjectOutputStream outStream = 
						conn.getObjectOutputStream();
				
				try {
					outStream.writeObject(update);
					outStream.reset();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private void updateClients()
	{
		if(!sceneNetwork.getLoadedScenes().isEmpty())
			sendUpdates(createUpdates());
	}
}
