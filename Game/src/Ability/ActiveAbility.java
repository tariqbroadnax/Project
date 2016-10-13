package Ability;

import java.time.Duration;
import java.util.Collection;

import Game.Entity;
import Game.Updatable;
import Utilities.EntityUtilities;
import Utilities.TimeUtilities;

public class ActiveAbility extends Ability implements Updatable
{
	private Duration cooldown,
					 castTime,
					 elapsedSinceCast;
	
	private double range;

	private boolean casting,
	 				activated;
	
	private boolean enabled;
		
	public ActiveAbility()
	{
		cooldown = 
		elapsedSinceCast = 
				Duration.ofMillis(100);
		
		castTime = Duration.ZERO;
	
		casting = 
		activated = false;
		
		enabled = true;
		
		range = 0;
	}
	
	public ActiveAbility(ActiveAbility ability)
	{
		super(ability);
	
		cooldown = ability.cooldown;
		castTime = ability.castTime;
		elapsedSinceCast = ability.elapsedSinceCast;
		
		casting = ability.casting;
		activated = ability.activated;
		
		enabled = true;		
		
		range = ability.range;
	}
		
	public boolean cast(AbilityEvent event)
	{
		if(canBeCast(event))
		{
			casting = true;
			elapsedSinceCast = Duration.ZERO;
			
			this.event = event;
			
			return true;
		}
		
		return false;
		/*
		else
			System.out.println("ability cannot be cast");*/
	}
	
	public void update(Duration delta)
	{
		elapsedSinceCast = 
				elapsedSinceCast.plus(delta);
		
		if(canBeActivated())
			activate();
	}
	
	public Collection<Entity> createAbilityEntities(Entity caster)
	{
		activated = false;
				
		return super.createAbilityEntities(caster);
	}
	
	public void setRange(double range)
	{
		this.range = range;
	}
	
	public void setCooldown(Duration cooldown)
	{
		this.cooldown = cooldown;
	}
	
	public void setCastTime(Duration castTime)
	{
		this.castTime = castTime;
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}	

	public boolean hasBeenActivated()
	{
		return activated;
	}
	
	public double getRange()
	{
		return range;
	}
	
	public boolean isCasting()
	{
		return casting;
	}
	
	public boolean canBeActivated()
	{
		return casting &&
			  TimeUtilities.greaterOrEqual(
				elapsedSinceCast, castTime);
	}
	
	private void activate()
	{
		casting = false;
		activated = true;
	}
	
	private boolean canBeCast(AbilityEvent e)
	{
		return enabled &&
			   TimeUtilities.greaterOrEqual(
			   elapsedSinceCast, cooldown)  &&
			   withinRange(e);
	}

	private boolean withinRange(AbilityEvent e)
	{
		if(range == 0)
			return true;
		else if(e.loc == null)
			return EntityUtilities.distanceSq(e.target, e.caster)
				   <= range * range;
		else
			return e.loc.distanceSq(e.caster.getLoc())
				   <= range * range;
	}

	public String toString()
	{
		String str = super.toString();
		
		str += "\ncooldown: " + cooldown.toMillis() +
			   "\ncast time: " + castTime.toMillis() +
			   "\nelapsed since cast: " + elapsedSinceCast.toMillis() +
			   "\ncasting: " + casting +
			   "\nactivated: " + activated +
			   "\nenabled: " + enabled +
			   "\nrange: " + range;
		
		return str;
	}
}
