package GameServer;

import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;

import Game.Player;

public class GameServerNetwork extends ServerNetwork
{
	private GameDatabase database;
	
	private ServerSceneNetwork sceneNetwork;

	private HashMap<ClientConnection, PlayerRecord> playerRecords;
	
	private Collection<ClientConnection> newConnections;

	public GameServerNetwork(
			GameDatabase database,
			ServerSceneNetwork sceneNetwork)
	{
		this.database = database;
	
		this.sceneNetwork = sceneNetwork;
		
		playerRecords = new HashMap<ClientConnection, PlayerRecord>();
		
		newConnections = new LinkedList<ClientConnection>();
	
		getClientConnector()
		.addServerConnectorListener(this);
	}
	
	protected void importConnection(ClientConnection conn)
	{
		super.importConnection(conn);
		
		PlayerRecord record = 
				database
				.getPlayerDatabase()
				.getPlayerRecord("","");
		
		playerRecords.put(conn, record);
		
		Player player = record.getPlayer();
		
		sceneNetwork.addEntity(player);
	
		newConnections.add(conn);
		
		/*
		 *  1. get record of player
		 *  2. add player to scene
		 *  3. send serverID of player
		 *  4. add record to map
		 */
	}
	
	protected void exportConnection(ClientConnection conn)
	{
		super.exportConnection(conn);
		
		PlayerRecord record = playerRecords.get(conn);
		
		Player player = record.getPlayer();
		
		playerRecords.remove(conn);
		sceneNetwork.removeEntity(player);
		
		/*
		 *  1. remove player from scene
		 *  2. remove record from map
		 *  3. save player record to database
		 */
	}
	
	public PlayerRecord getPlayerRecord(ClientConnection conn)
	{
		return playerRecords.get(conn);
	}
	
	public Collection<ClientConnection> getNewConnections()
	{
		 return newConnections;
	}
}
