package Actions;

import static Utilities.MathUtilities.GREATER;
import static Utilities.MathUtilities.flat;

import java.time.Duration;

import Game.Entity;
import Game.Updatable;
import GameClient.GameClientResources;
import GameServer.GameServerResources;
import Graphic.Vector2D;
import Movement.LooseMovement;
import Movement.MovementComponent;

public class PlayerMovementHandler implements Updatable
{
	private Vector2D.Double direction;
	
	private boolean moving;
	
	private Duration elapsedNoVertMovement,
					 elapsedNoHorizMovement,
					 elapsedBeforeStop;
	
	private boolean vertMovementShouldStop,
					horizMovementShouldStop;
	
	private Entity player;
	
	public PlayerMovementHandler(Entity player)
	{
		this.player = player;

		direction = new Vector2D.Double();
		
		moving = false;
		
		elapsedNoVertMovement = 
		elapsedNoHorizMovement = Duration.ZERO;
		
		elapsedBeforeStop = Duration.ofMillis(100);
	}
	
	@Override
	public void update(Duration delta)
	{
		if(!moving) return;
		
		updateElapsedNoMovement(delta);
		
		findOutIfMovementShouldStop();

		if(someMovementShouldStop())
			stopMovement();
		
		if(moving)
			startMovement();
	}
	
	private void updateElapsedNoMovement(Duration delta)
	{
		elapsedNoVertMovement = 
				elapsedNoVertMovement.plus(delta);
		
		elapsedNoHorizMovement = 
				elapsedNoHorizMovement.plus(delta);
	}
	
	private void stopMovement()
	{
		if(vertMovementShouldStop)
			direction.y = 0;
		if(horizMovementShouldStop)
			direction.x = 0;	
		
		if(vertMovementShouldStop &&
		   horizMovementShouldStop)
		{
			LooseMovement movement =
					(LooseMovement)
					player.get(MovementComponent.class)
					  	  .getNormalMovement();
			
			movement.setEnabled(false);
			
			moving = false;
		}				
	}

	private void startMovement()
	{
		LooseMovement movement =
				(LooseMovement)
				player.get(MovementComponent.class)
				  	  .getNormalMovement();
		
		movement.setEnabled(true);
		movement.setDirection(direction.angle());	 
	}
	
	private void findOutIfMovementShouldStop()
	{
		vertMovementShouldStop =
				movementShouldStop(elapsedNoVertMovement);
		horizMovementShouldStop =
				movementShouldStop(elapsedNoHorizMovement);		
	}

	private boolean movementShouldStop(Duration elapsedNoMovement)
	{
		return flat(elapsedNoMovement.compareTo(elapsedBeforeStop))
			   == GREATER;	
	}
	
	private boolean someMovementShouldStop()
	{
		return vertMovementShouldStop ||
			   horizMovementShouldStop;
	}

	public void moveUp()
	{
		startVertMovement();
		direction.y = -1;
	}
	
	public void moveDown()
	{
		startVertMovement();
		direction.y = 1;
	}
	
	public void moveLeft()
	{
		startHorizMovement();
		direction.x = -1;
	}
	
	public void moveRight()
	{
		startHorizMovement();
		direction.x = 1;
	}
	
	private void startHorizMovement()
	{
		moving = true;
		elapsedNoHorizMovement = Duration.ZERO;
	}
	
	private void startVertMovement()
	{
		moving = true;
		elapsedNoVertMovement = Duration.ZERO;
	}
	
	public static class MoveUpAction extends PlayerAction
	{
		public MoveUpAction(GameClientResources resources)
		{	super(resources);	}

		public void executeOnClient(){ resources.getMovementHandler().moveUp(); }

		@Override
		public void executeOnServer(GameServerResources resources) {
			// TODO Auto-generated method stub
			
		}		
	}
	
	public static class MoveDownAction extends PlayerAction
	{
		public MoveDownAction(GameClientResources resources)
		{	super(resources);	}

		public void executeOnClient(){ resources.getMovementHandler().moveDown(); }
		
		@Override
		public void executeOnServer(GameServerResources resources) {
			// TODO Auto-generated method stub
			
		}	
	}
	
	public static class MoveLeftAction extends PlayerAction
	{
		public MoveLeftAction(GameClientResources resources)
		{	super(resources);	}

		public void executeOnClient(){ resources.getMovementHandler().moveLeft(); }	
		
		@Override
		public void executeOnServer(GameServerResources resources) {
			// TODO Auto-generated method stub
			
		}	
	}
	
	public static class MoveRightAction extends PlayerAction
	{
		public MoveRightAction(GameClientResources resources)
		{	super(resources);	}

		public void executeOnClient(){ resources.getMovementHandler().moveRight(); }
		
		@Override
		public void executeOnServer(GameServerResources resources) {
			// TODO Auto-generated method stub
			
		}	
	}
}
