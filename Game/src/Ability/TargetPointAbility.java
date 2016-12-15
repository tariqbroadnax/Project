package Ability;

import java.awt.geom.Point2D;

public class TargetPointAbility extends TargetAbility
{
	protected Point2D.Double target;
	
	public boolean canBeCast()
	{
		return super.canBeCast() && 
			   target != null && inRange();
	}
	
	protected boolean inRange()
	{
		Point2D.Double srcLoc = src.getLoc();
	
		// System.out.println(range * range + " " + srcLoc.distanceSq(target));
		return range * range > srcLoc.distanceSq(target);
	}
	
	public void setTarget(Point2D.Double target) {
		this.target = target;
	}
}
