package Ability;

import java.time.Duration;
import java.util.List;

import Game.Updatable;
import Modifiers.Effect;

public abstract class ActiveAbility extends Ability
{
	private long cooldown, castTime,
				 elapsedSinceCast;
	
	private boolean casting, enabled;
	
	protected RangeIndicator indicator;
		
	public ActiveAbility() 
	{
		cooldown = 0;	
		castTime = 0;
		elapsedSinceCast = 500;
	
		casting = false;
		enabled = true;		
	}
	
	public ActiveAbility(ActiveAbility ability)
	{
		cooldown = ability.cooldown;
		castTime = ability.castTime;
		
		casting = false;
		enabled = true;
	}
		
	public void update(Duration delta)
	{
		elapsedSinceCast += delta.toMillis();
		
		if(canBeActivated()) 
			activate();
	}
	
	public void cast()
	{
		if(canBeCast())
		{
			if(castTime == 0)
				activate();
			else
				casting = true;
			
			elapsedSinceCast = 0;	
		}
	}
	
	protected void activate()
	{
		casting = false;
	}
			
	public void setCooldown(long cooldown) 
	{
		if(cooldown < 0 || cooldown < castTime)
			throw new IllegalArgumentException();
		
		this.cooldown = cooldown;
	}
	
	public void setCastTime(long castTime) 
	{
		if(castTime < 0 || castTime > cooldown)
			throw new IllegalArgumentException();

		this.castTime = castTime;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}	

	public boolean isCasting() {
		return casting;
	}
	
	public boolean onCooldown() {
		return elapsedSinceCast < cooldown;
	}

	public boolean canBeCast()
	{
		return enabled && elapsedSinceCast >= cooldown;
	}

	protected boolean canBeActivated()
	{
		return casting && 
			   elapsedSinceCast >= castTime;	
	}
	
	public long getCastTime() {
		return castTime;
	}
	
	public long getElapsedSinceCast() {
		return elapsedSinceCast;
	}
	
	public RangeIndicator getRangeIndicator() {
		return indicator;
	}

	public String toString()
	{
		String str = super.toString();
		
		str += "\ncooldown: " + cooldown +
			   "\ncast time: " + castTime +
			   "\nelapsed since cast: " + elapsedSinceCast +
			   "\ncasting: " + casting +
			   "\nenabled: " + enabled;
		
		return str;
	}
}
