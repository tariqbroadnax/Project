package GameServer;

public class GameServerResources 
{	
	private final GameDatabase database;
	
	private final ServerUpdater updater;
	
	private final ServerSceneNetwork sceneNetwork;

	private final GameServerNetwork network;
		
	private final ServerScene scene;
	
	public GameServerResources()
	{			
		database = new GameDatabase();
		
		sceneNetwork = new ServerSceneNetwork();
		
		network = new GameServerNetwork(database, sceneNetwork);

		updater = new ServerUpdater(network, sceneNetwork);
	
		scene = new ServerScene();	
	}
}
	
