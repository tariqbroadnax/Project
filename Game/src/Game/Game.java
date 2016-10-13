package Game;

public class Game
{	
	private Resources resources;
	
	public Game()
	{
		resources = new Resources();	
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
