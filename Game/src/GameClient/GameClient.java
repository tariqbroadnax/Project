package GameClient;

public class GameClient 
{
	private GameClientResources resources;
		
	public GameClient()
	{
		resources = new GameClientResources();
	}
	
	public void start()
	{		
		resources.getGUI()
				 .setVisible(true);
		
		resources.getUpdater()
				 .start();
	}
	
	public void stop() 
	{
		resources.getGUI()
		 		 .setVisible(false);
		
		resources.getUpdater()
				 .stop();
		
	}
}
