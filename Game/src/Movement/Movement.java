package Movement;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.Duration;

import Maths.Vector2D;

public class Movement implements Cloneable, Serializable
{
	protected double speed, dir;
	
	private boolean enabled = true;
	
	public Movement()
	{
		speed = 0;
		
		enabled = false;
	}
	
	public Movement(Movement movement)
	{
		speed = movement.speed;
		
		enabled = false;
	}

	public void move(Point2D.Double currLoc, Duration delta)
	{		
		if(!enabled) return;
		
		double t = delta.toNanos() / 1E9;

		Vector2D.Double vel = Vector2D.Double.direction(dir)
							   				 .getScaled(speed),
						offset = vel.getScaled(t);
		
		offset.move(currLoc);		
	}
	
	public void setSpeed(double speed) {
		this.speed = speed;
	}
	
	public void setDirection(double dir) {
		this.dir = dir;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public double getDirection() {
		return dir;
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public Object clone() {
		return null;
	}
}
