package Movement;

import java.awt.geom.Point2D.Double;
import java.time.Duration;
import java.util.LinkedList;
import java.util.Queue;

import static Utilities.MathUtilities.*;

public class TargetedMovement extends Movement
{
	private Queue<Double> targetLocs;
	
	public TargetedMovement()
	{
		targetLocs = new LinkedList<Double>();
	}
	
	public TargetedMovement(TargetedMovement movement)
	{
		this();
		
		targetLocs.addAll(movement.targetLocs);
	}
	
	@Override
	protected void _move(Double currLoc, Duration delta)
	{				
		if(targetLocs.isEmpty()) return;
		
		
		Double targetLoc = targetLocs.peek();
		
		Duration durUntilLoc = 
				durationUntilLoc(currLoc, targetLoc);
				
		if(durUntilLoc.compareTo(delta) < 0)
		{
			moveOntoTarget(currLoc);
			continueMovement(currLoc, durUntilLoc, delta);
		}
		else
		{
			double angle = angleFrom(currLoc, targetLoc);	
			
			LooseMovement.move(currLoc, angle, speed, delta);
		}
	}
	
	private void moveOntoTarget(Double currLoc)
	{
		currLoc.setLocation(targetLocs.poll());
	}
	
	private void continueMovement(Double currLoc, 
			Duration durUntilLoc, Duration delta)
	{
		Duration remainingDuration =
				delta.minus(durUntilLoc);
		
		_move(currLoc, remainingDuration);
	}
	// d = 1000 * s = 1000 * (x2 - x) / v
	
	private Duration durationUntilLoc(Double currLoc, Double targetLoc)
	{
		double angle = angleFrom(currLoc, targetLoc),
			   xVel = speed * Math.cos(angle),
			   t = (targetLoc.x - currLoc.x) / xVel;
				
		return Duration.ofNanos((long)(1000000000 * t));
	}
	
	public void setTarget(Double targetLoc)
	{
		targetLocs.clear();
		targetLocs.add(targetLoc);		
	}
	
	public void addTargets(Double... targetLocs)
	{
		for(int i = 0; i < targetLocs.length; i++)
			this.targetLocs.add(targetLocs[i]);
	}

	@Override
	protected Movement _clone()
	{
		return new TargetedMovement(this);
	}
}
