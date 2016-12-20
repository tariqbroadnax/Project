package Behaviour;

import java.awt.geom.Point2D.Double;
import java.time.Duration;
import java.util.LinkedList;
import java.util.Queue;

import Entity.Entity;
import Maths.Maths;
import Movement.Movement;
import Movement.MovementComponent;
import Movement.MovementListener;

public class PathingBehaviour extends Behaviour
	implements MovementListener
{
	private Queue<Double> targetLocs;
	
	private Movement srcMovement;
	
	public PathingBehaviour() {
		targetLocs = new LinkedList<Double>();
	}

	@Override
	public void update(Duration delta) 
	{
		// System.out.println("pathing...");
		
		if(targetLocs.isEmpty()) { 
			srcMovement.setEnabled(false);
			return;
		}

		srcMovement.setEnabled(true);

		Double srcLoc = src.getLoc(),
			   targetLoc = targetLocs.peek();
		
		Duration durUntilLoc = 
				durationUntilLoc(srcLoc, targetLoc);
				
		if(durUntilLoc.compareTo(delta) < 0)
		{
			moveOntoTarget();
			
			Duration remaining =
					delta.minus(durUntilLoc);
			
			update(remaining);
		}
		else
		{
			double dir = Maths.angleFrom(srcLoc, targetLoc);	
			
			srcMovement.setDirection(dir);
		}	
	}
	
	private void moveOntoTarget() {
		src.setLoc(targetLocs.poll());
	}

	// d = 1000 * s = 1000 * (x2 - x) / v
	private Duration durationUntilLoc(Double currLoc, Double targetLoc)
	{
		double speed = srcMovement.getSpeed();
		
		double angle = Maths.angleFrom(currLoc, targetLoc),
			   xVel = speed * Math.cos(angle),
			   t = (targetLoc.x - currLoc.x) / xVel;
				
		return Duration.ofNanos((long)(1000000000 * t));
	}
	
	public void setSrc(Entity src) 
	{
		super.setSrc(src);
		
		srcMovement = src.get(MovementComponent.class)
						 .getNormalMovement();
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
	
	public boolean hasTargets() {
		return targetLocs.size() != 0;
	}
	
	public void clearTargets() {
		targetLocs.clear();
	}
}
