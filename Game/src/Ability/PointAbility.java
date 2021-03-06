package Ability;

import java.awt.geom.Point2D;

public class PointAbility extends TargetAbility
{
	protected Point2D.Double target;
		
	public PointAbility()
	{
		
	}
	
	public boolean canBeCast()
	{
		return super.canBeCast() && 
			   target != null && inRange();
	}
	
	protected boolean inRange()
	{
		Point2D.Double srcLoc = src.getLoc();
	
		return range * range > srcLoc.distanceSq(target);
	}
	
	public void setTarget(Point2D.Double target) {
		this.target = target;
	}
	
	public Point2D.Double getTarget() {
		return target;
	}
}
