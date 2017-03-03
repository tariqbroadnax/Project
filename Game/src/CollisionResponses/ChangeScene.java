package CollisionResponses;

import java.awt.geom.Point2D;

import Entity.Entity;
import EntityComponent.CollisionEvent;
import EntityComponent.CollisionResponse;
import Game.Game;

public class ChangeScene implements CollisionResponse
{
	private String fileName = "C:\\Users\\Tariq Broadnax\\Desktop\\scn";
	
	@Override
	public void collisionOccurred(CollisionEvent e) 
	{
		Game game = e.collided.getSceneLoc()
				  	 .getGame();
				
		Entity player = game.getPlayer();
		
		if(e.collided == player || e.collider == player)
		{
			game.getScheduler()
			    .schedule(() -> game.changeScene(fileName, new Point2D.Double(-50, -50)),
			    		  0);
		}
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getFileName() {
		return fileName;
	}
	
	public Object clone()
	{
		return null;
	}
}
