package Behaviour;

import java.awt.geom.Point2D;
import java.time.Duration;

import Ability.FreeProjectileAbility;
import Ability.HomingProjectileAbility;
import EntityComponent.AbilityComponent;
import Game.Entity;
import Movement.Movement;
import Movement.MovementComponent;
import Utilities.Math2;

public class BasicAttackBehaviour extends Behaviour
{
	private Entity target;

	private Movement movement;
	private AbilityComponent comp;
	private HomingProjectileAbility attack;
	
	private boolean movingCloser;
	
	@Override
	public void update(Duration delta) 
	{		
		if(target == null) return;
		if(movingCloser)
		{
			movingCloser = false;
			movement.setEnabled(false);
		}
		
		if(targetInRange())
		{
			attack.setTarget(target);
			
			if(attack.canBeCast())
				attack.cast();
		}
		else
			moveCloser();
	}
	
	private boolean targetInRange()
	{		
		Point2D.Double srcLoc = src.getLoc(),
					   targetLoc = target.getLoc();
		
		double dist = srcLoc.distance(targetLoc),
			   range = attack.getRange();
		 
		return dist < range;
	}
	
	private void moveCloser()
	{
		movingCloser = true;
		
		Point2D.Double srcLoc = src.getLoc(),
					   targetLoc = target.getLoc();
		
		double angle = Math2.angleFrom(srcLoc, targetLoc);
		
		movement.setDirection(angle);
		movement.setEnabled(true);
	}
	
	public void setSrc(Entity src)
	{
		super.setSrc(src);
		
		comp = src.get(AbilityComponent.class);
		attack = comp.getBasicAttack();
	
		movement = src.get(MovementComponent.class)
					  .getNormalMovement();
	
		// movement.setSpeed(10);
	}
	
	public void setTarget(Entity target)
	{
		this.target = target;
	}
	
	public boolean hasTarget()
	{
		return target != null;
	}
	
	public void removeTarget()
	{
		target = null;
	}
	
	public Entity getTarget() {
		return target;
	}
}