package Actions;

import java.awt.geom.Point2D;
import java.time.Duration;

import Entity.Entity;
import Movement.Force;
import Movement.MovementComponent;

public class Move implements Action
{
	private Entity actor;
	
	private Force force;
	
	private Point2D.Double target;
	
	private boolean finished;
	
	private double tolerance;
	
	public Move()
	{
		this(0, 0, 0);
	}
	
	public Move(double speed, double targetX, double targetY)
	{
		force = new Force();
		
		target = new Point2D.Double(targetX, targetY);
		
		force.setSpeed(speed);
	
		tolerance = 0.25;
		
		finished = false;
	}

	@Override
	public void update(Duration delta) 
	{
		if(finished) return;
		
		Point2D.Double src = actor.getLoc();
			
		double distSq = src.distanceSq(target);
				
		if(distSq > tolerance)
		{
			double dir = Math.atan2(target.y - src.y,
									target.x - src.x);
			
			force.setDirection(dir);
		}
		else
			finished = true;
	}

	@Override
	public void dispose() 
	{
		if(actor != null)
			actor.get(MovementComponent.class)
				 .getMovement()
				 .removeForce(force);
	}

	@Override
	public void setActor(Entity actor) 
	{
		dispose(); // prev actor
		
		this.actor = actor;
	
		actor.get(MovementComponent.class)
		 	 .getMovement()
		 	 .addForce(force);
		
		finished = false;
	}
	
	public void setSpeed(double speed) {
		force.setSpeed(speed);
	}
	
	public void setTarget(double x, double y) {
		target.x = x; target.y = y;
	}
	
	public void setTolerance(double tolerance) {
		this.tolerance = tolerance;
	}

	@Override
	public boolean isFinished() {
		return finished;
	}
}