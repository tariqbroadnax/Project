package Movement;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.Duration;

public abstract class Movement 
implements Cloneable, Serializable
{
	protected double speed;
	
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
		if(enabled)
			_move(currLoc, delta);
	}
	
	protected abstract void _move(Point2D.Double currLoc, Duration delta);
	
	public void setSpeed(double speed)
	{
		this.speed = speed;
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public double getSpeed()
	{
		return speed;
	}
	
	public boolean isEnabled()
	{
		return enabled;
	}
	
	public Object clone()
	{
		return _clone();
	}
	
	protected abstract Movement _clone();
}
