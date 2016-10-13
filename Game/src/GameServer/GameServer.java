package GameServer;

import java.io.IOException;

public class GameServer 
{	
	private final GameDatabase database;
	
	private final ServerUpdater updater;
	
	private final ServerSceneNetwork sceneNetwork;

	private final GameServerNetwork network;
			
	public GameServer()
	{
		database = new GameDatabase();
		
		sceneNetwork = new ServerSceneNetwork();
		
		network = new GameServerNetwork(database, sceneNetwork);

		updater = new ServerUpdater(network, sceneNetwork);	
	}
	
	public void start()
	{
		updater.start();
	}
	
	public void stop()
	{
		updater.stop();
		
		try {
			network.getClientConnector()
				   .closeConnections();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
