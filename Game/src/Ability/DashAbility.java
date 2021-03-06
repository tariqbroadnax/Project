package Ability;

import java.awt.geom.Point2D;
import java.time.Duration;

import Movement.Force;
import Movement.Movement;
import Movement.MovementComponent;

public class DashAbility extends PointAbility
{
	private Force force;
	
	private boolean dashing;
	private long elapsed;
	
	public DashAbility()
	{
		force = new Force(500, 0);
		
		setCooldown(500);
	}
	
	protected void activate()
	{
		super.activate();
		
		Point2D.Double src = this.src.getLoc();
		
		double angle = Math.atan2(target.y - src.y,
								  target.x - src.x);
		
		force.setDirection(angle);
		
		dashing = true;
		
		elapsed = 0;
		
		if(this.src.contains(MovementComponent.class))
		{
			Movement movement = this.src.get(MovementComponent.class)
										.getMovement();
			
			movement.addForce(force);
		}
	}
	
	private void unactivate()
	{
		dashing = false;
		
		if(this.src.contains(MovementComponent.class))
		{
			Movement movement = this.src.get(MovementComponent.class)
										.getMovement();
			
			movement.removeForce(force);
		}
	}
	
	public void update(Duration delta)
	{
		super.update(delta);
		
		if(dashing)
		{
			elapsed += delta.toMillis();
			
			if(elapsed > 200)
				unactivate();
		}
	}
}
