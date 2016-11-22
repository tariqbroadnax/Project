package AI;

import static Utilities.EntityUtilities.getLoc;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.PriorityQueue;

import Game.Entity;
import Movement.MovementComponent;
import Movement.TargetedMovement;
import TestEntity.TestEntity;
import Utilities.EntityUtilities;

public class AssaultBehaviour extends Behaviour
{
	private Entity nearestPlayer;
	
	private double minRange;
	
	public AssaultBehaviour()
	{
		minRange = 5;
	}
	
	@Override
	public boolean isValid(Collection<Entity> entities)
	{		
		nearestPlayer = 
				EntityUtilities.nearestPlayer(
						EntityUtilities.getLoc(target), entities);
		
		return nearestPlayer != null && !targetClose();
	}
	
	private boolean targetClose()
	{
		return EntityUtilities.
				distanceSq(target, nearestPlayer) < 
				minRange * minRange;
	}
	
	@Override
	protected Behaviour _clone()
	{
		return new AssaultBehaviour();
	}

	@Override
	protected void _apply()
	{		
		TargetedMovement movement =
				(TargetedMovement)
				target.get(MovementComponent.class)
					  .getNormalMovement();
		
		movement.setEnabled(true);
		movement.setTarget(getLoc(nearestPlayer));
	}

	@Override
	protected void _revert()
	{
		TargetedMovement movement =
				(TargetedMovement)
				target.get(MovementComponent.class)
					  .getNormalMovement();
		
		movement.setEnabled(false);
		
		nearestPlayer = null;
	}
}
