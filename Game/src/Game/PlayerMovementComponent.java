package Game;

import Movement.LooseMovement;
import Movement.MovementComponent;
import Movement.TargetedMovement;

public class PlayerMovementComponent extends MovementComponent
{	
	private TargetedMovement targetedMovement;
	
	private LooseMovement looseMovement;
	
	public PlayerMovementComponent()
	{
		super();
		
		targetedMovement = new TargetedMovement();
		looseMovement = new LooseMovement();
	}
	
	public void setNormalMovementAsTargetted()
	{
		normalMovement = targetedMovement;
	}
	
	public void setNormalMovementAsLoose()
	{
		normalMovement = looseMovement;
	}
	
	public TargetedMovement getTargetedMovement()
	{
		return targetedMovement;
	}
	
	public LooseMovement getLooseMovement()
	{
		return looseMovement;
	}
}
