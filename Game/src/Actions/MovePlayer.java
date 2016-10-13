package Actions;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import Game.Player;
import GameClient.ClientCommand;
import GameClient.GameClientResources;
import GameServer.ServerSceneNetwork;
import Movement.MovementComponent;
import Movement.TargetedMovement;

public class MovePlayer extends MouseAdapter
	implements ClientCommand						
{
	private transient GameClientResources resources;
		
	private Point2D.Double targetLoc;
	
	public MovePlayer(GameClientResources resources) 
	{
		this.resources = resources;
	}

	@Override
	public void execute(Player player, ServerSceneNetwork sceneNetwork) 
	{
		TargetedMovement movement = 
				(TargetedMovement)
				player.get(MovementComponent.class)
					  .getNormalMovement();
		
		movement.setTarget(targetLoc);
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		Point pressLoc = e.getPoint();
		
		targetLoc =
				resources
				.getCamera()
				.screenLocToNormalLoc(pressLoc);
		
		resources.getClientNetwork()
				 .getServerConnector()
				 .getServerConnection()
				 .sendCommand(this);
	}
}
