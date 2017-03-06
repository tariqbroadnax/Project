package Behavior;

import java.awt.geom.Point2D;
import java.time.Duration;

import Entity.Entity;
import Movement.Force;
import Movement.MovementComponent;

public class Follow extends Behavior
{
	private Entity target;
	
	private Force force;
	
	private double speed, minDist;
	
	public Follow(Entity target)
	{
		this.target = target;
		
		force = new Force();
	
		speed = 40; minDist = 0;
	}
	
	public void start()
	{		
		src.get(MovementComponent.class)
		   .getMovement()
		   .addForce(force);
	}
	
	@Override
	public void update(Duration delta) 
	{
		force.setSpeed(0);
		
		if(targetTooFar())
		{
			moveCloser();
		}
	}
	
	private boolean targetTooFar() 
	{
		Point2D.Double srcLoc = src.getLoc(),
					   targetLoc = target.getLoc();

		return srcLoc.distanceSq(targetLoc) > minDist * minDist;
	}
	
	private void moveCloser()
	{
		Point2D.Double srcLoc = src.getLoc(),
				   targetLoc = target.getLoc();
	
		double angle = Math.atan2(targetLoc.y - srcLoc.y,
								  targetLoc.x - srcLoc.x);
		
		force.setDirection(angle);
		force.setSpeed(speed);
	}
}
