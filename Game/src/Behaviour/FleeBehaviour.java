package Behaviour;

import java.awt.geom.Point2D;
import java.time.Duration;

import Entity.Entity;
import Maths.Maths;
import Movement.Movement;
import Movement.MovementComponent;

public class FleeBehaviour extends Behaviour
{
	private double minDist;
	
	private Entity target;
	
	private Movement srcMovement;
	
	public FleeBehaviour()
	{
		minDist = 1600;
		
		target = null;
	}

	@Override
	public void update(Duration delta) 
	{
		if(target == null) {
			srcMovement.setEnabled(false);
			return; 
		}
		
		srcMovement.setEnabled(true);
		
		Point2D.Double srcLoc = src.getLoc(),
				   targetLoc = target.getLoc();
	
		double dist = srcLoc.distanceSq(targetLoc);
				
		if(dist < minDist)
		{
			double dir = Maths.angleFrom(targetLoc, srcLoc);
			
			srcMovement.setDirection(dir);
			srcMovement.setEnabled(true);
		}
		else
			srcMovement.setEnabled(false);

	}
	
	public void setSrc(Entity src)
	{
		super.setSrc(src);
		
		srcMovement = src.get(MovementComponent.class)
						 .getNormalMovement();
	}
	
	public void setTarget(Entity target) {
		this.target = target;
	}

	public void setMinDist(double minDist) {
		this.minDist = minDist;
	}
	
}
