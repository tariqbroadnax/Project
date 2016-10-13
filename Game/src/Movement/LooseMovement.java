package Movement;

import java.awt.geom.Point2D;
import java.time.Duration;

import Graphic.Vector2D;

public class LooseMovement extends Movement
{
	private double radians;
	
	public LooseMovement()
	{
		radians = 0;
	}
	
	public LooseMovement(LooseMovement movement)
	{
		super(movement);
		
		radians = movement.radians;
	}
	
	protected void _move(Point2D.Double currLoc, Duration delta)
	{
		move(currLoc, radians, speed, delta);
	}
	
	public static void move(Point2D.Double currLoc, double angle, double speed, Duration delta)
	{
		double t = delta.toNanos() / 1000000000.0;

		Vector2D.Double vel = Vector2D.Double
							.direction(angle)
							.getScaled(speed),
						offset = vel.getScaled(t);
		
		offset.move(currLoc);
	}

	public void setDirection(double radians)
	{
		this.radians = radians;
	}
	
	public double getDirection()
	{
		return radians;
	}

	@Override
	protected Movement _clone()
	{
		return new LooseMovement(this);
	}
}
