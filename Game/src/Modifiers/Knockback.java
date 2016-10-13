package Modifiers;

import java.time.Duration;

import Movement.LooseMovement;
import Movement.MovementComponent;
import Utilities.MathUtilities;

public class Knockback extends Modifier
{
	protected double speed;
		
	public Knockback()
	{
		this(100);
	}
	
	public Knockback(double speed)
	{
		super();
		
		this.speed = speed;
		
		this.lifetime = Duration.ofMillis(125);
	}
	
	public Knockback(Knockback knockback)
	{
		super(knockback);
		
		speed = knockback.speed;
	}
	
	@Override
	protected void apply()
	{	
		LooseMovement disablingMovement =
				new LooseMovement();
		
		disablingMovement.setDirection(findDirection());
		disablingMovement.setSpeed(speed);
		
		MovementComponent comp =
				target.get(MovementComponent.class);
		
		comp.setEnabled(false);
		comp.setDisablingMovement(disablingMovement);
	}
	
	private double findDirection()
	{
		return MathUtilities.angleFrom(
				src.getLoc(), target.getLoc());
	}

	public void revert()
	{
		target.get(MovementComponent.class)
			  .setEnabled(true);		
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}
	
	@Override
	protected Object _clone()
	{
		return new Knockback(this);
	}
}
