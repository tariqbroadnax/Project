package Actions;

import java.awt.geom.Point2D;
import java.time.Duration;

import Entity.Entity;
import Movement.Force;
import Movement.MovementComponent;

public class Follow extends Action 
{	
	private Entity target;
	
	private Force force;

	private double maxDistSq, prevDistSq;
	
	private double speed;

	public Follow()
	{
		force = new Force();
		
		maxDistSq = 100;
		
		speed = 25;
	}
	
	public void update(Duration delta)
	{
		double distSq = actorTargetDistSq();
	
		if(distSq > maxDistSq)
		{
			if(prevDistSq < maxDistSq)
			{
				actor.get(MovementComponent.class)
					 .getMovement()
					 .addForce(force);
			}		
			
			double angle = actorTargetAngle();
			
			force.setDirection(angle);
			
			double t = delta.toMillis() / 1000.0;
			
			if(distSq < Math.pow(speed * t, 2))
				force.setSpeed(Math.sqrt(distSq) / t);
			else
				force.setSpeed(speed);
		}
		else if(prevDistSq > maxDistSq)
		{
			actor.get(MovementComponent.class)
			 	 .getMovement()
			 	 .removeForce(force);
		}
		
		prevDistSq = distSq;
	}
	
	public void setTarget(Entity target)
	{
		this.target = target;
	}
	
	private double actorTargetDistSq()
	{
		Point2D.Double actorLoc = actor.getLoc(),
					   targetLoc = target.getLoc();
		
		return actorLoc.distanceSq(targetLoc);
	}
	
	private double actorTargetAngle()
	{
		Point2D.Double actorLoc = actor.getLoc(),
					   targetLoc = target.getLoc();
		
		return Math.atan2(targetLoc.y - actorLoc.y,
						  targetLoc.x - actorLoc.x);
	}
}
