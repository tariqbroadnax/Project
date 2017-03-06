package Ability;

import java.awt.geom.Point2D;

import Entity.Entity;

public class TargetUnitAbility extends TargetAbility
{
	protected Entity target;
	
	public TargetUnitAbility()
	{
		super();
	}
	
	public TargetUnitAbility(TargetUnitAbility ability)
	{
		super(ability);
	}
	
	public boolean canBeCast()
	{
//		System.out.println(super.canBeCast() + " " + (target != null) + " " + inRange());
		
		return super.canBeCast() && 
			   target != null && inRange();
	}
	
	protected boolean inRange()
	{
		Point2D.Double srcLoc = src.getLoc(),
					   targetLoc = target.getLoc();
		
		return range * range >= srcLoc.distanceSq(targetLoc);
	}
	
	public void setTarget(Entity target) {
		this.target = target;
	}
	
	public Object clone()
	{
		return new TargetUnitAbility(this);
	}
}
