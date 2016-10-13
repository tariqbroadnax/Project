package GameServer;

public class GameDatabase 
{
	private SceneDatabase sceneDatabase;
	
	private PlayerDatabase playerDatabase;
	
	public GameDatabase()
	{
		sceneDatabase = new SceneDatabase();
	
		playerDatabase = new PlayerDatabase();
	}
	
	public SceneDatabase getSceneDatabase()
	{
		return sceneDatabase;
	}
	
	public PlayerDatabase getPlayerDatabase()
	{
		return playerDatabase;
	}

}
