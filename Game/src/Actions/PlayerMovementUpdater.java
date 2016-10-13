package Actions;

import java.time.Duration;

import Game.Player;
import Game.PlayerMovementComponent;
import Game.Resources;
import Game.Updatable;
import Maths.Direction;

public class PlayerMovementUpdater
	implements Updatable
{
	private Direction vertDir,
					  horizDir;
	
	private Player player;
	
	private Duration elapsedNoMovement;

	public PlayerMovementUpdater(Player player)
	{
		this.player = player;
	}
	
	@Override
	public void update(Duration delta) 
	{
		if(vertDir == null && horizDir == null) return;
		else
		{
			PlayerMovementComponent comp =
					player.getMovementComponent();
			
			comp.setNormalMovementAsLoose();
			
			double angle = Direction.getSum(vertDir, horizDir)
									.angle();

			comp.getLooseMovement()
				.setDirection(angle);				
		}
	}

	public void addDirection(Direction dir)
	{
		if(horizDir == null && vertDir == null)
		{
			if(dir.isHorizontal())
				horizDir = dir;
			else
				vertDir = dir;
			
			player.getMovementComponent()
				  .getLooseMovement()
				  .setEnabled(true);
		}
		else
		{
			if(dir.isHorizontal())
				horizDir = dir;
			else
				vertDir = dir;
		}
	}
	
	public void removeDirection(Direction dir)
	{
		if(dir.isVertical() && vertDir == dir)
			vertDir = null;
		else if(dir.isHorizontal() && horizDir == dir)
			horizDir = null;
		
		if(vertDir == null && horizDir == null)
		{
			player.getMovementComponent()
			  .getLooseMovement()
			  .setEnabled(false);
		}
	}
	
	public static class Move extends SyncGameAction
	{
		private Direction dir;
		
		public Move(
				Resources resources, Direction dir) 
		{
			super(resources);
			
			this.dir = dir;
		}

		@Override
		protected void invoke() 
		{
			resources.getPlayerMovementUpdater()
					 .addDirection(dir);
		}
	}
	
	public static class UnMove extends SyncGameAction
	{
		private Direction dir;
		
		public UnMove(
				Resources resources, Direction dir) 
		{
			super(resources);
			
			this.dir = dir;
		}

		@Override
		protected void invoke() 
		{
			resources.getPlayerMovementUpdater()
					 .removeDirection(dir);
		}
	}
}
