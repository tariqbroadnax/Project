package Ability;

import java.time.Duration;

import EntityComponent.StatsComponent;
import Stat.Stats;

public abstract class ActiveAbility extends Ability
{
	private long[] cooldowns, castTimes;
	
	private long elapsedSinceCast;
	
	private double[] manaCosts;
	
	private boolean casting, enabled;
	
	protected RangeIndicator indicator;
		
	private Stats stats;
	
	public ActiveAbility() 
	{
		cooldowns = new long[maxLvl];
		castTimes = new long[maxLvl];

		manaCosts = new double[maxLvl];
		
		elapsedSinceCast = 500;
		
		casting = false;
		enabled = true;		
		
		for(int i = 0; i < maxLvl; i++)
		{
			cooldowns[i] = castTimes[i] = 0;
			
			manaCosts[i] = 5;
		}
	}
	
	public ActiveAbility(ActiveAbility ability)
	{
		this();
		
		for(int i = 0; i < maxLvl; i++)
		{
			cooldowns[i] = ability.cooldowns[i];
			castTimes[i] = ability.castTimes[i];
			manaCosts[i] = ability.manaCosts[i];
		}
	}
	
	public void start()
	{
		stats = src.get(StatsComponent.class)
				   .getStats();
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
			double manaCost = manaCosts[lvl];
			long castTime = castTimes[lvl];
			
			stats.useMana(manaCost);

			if(castTime == 0)
				activate();
			else
				casting = true;
			
			elapsedSinceCast = 0;	
		}
	}
	
	public void stopCast() {
		casting = false;
	}
	
	protected void activate() {
		casting = false;
	}
			
	public void setCooldown(long cooldown) 
	{
		for(int lvl = 1; lvl <= maxLvl; lvl++)
			setCooldown(cooldown, lvl);
	}
	
	public void setCooldown(long cooldown, int lvl)
	{
		if(cooldown < 0 || cooldown < castTimes[lvl-1])
			throw new IllegalArgumentException();
		
		cooldowns[lvl-1] = cooldown;
	}
	
	public void setCastTime(long castTime)
	{
		for(int lvl = 1; lvl <= maxLvl; lvl++)
			setCastTime(castTime, lvl);
	}
	
	public void setCastTime(long castTime, int lvl) 
	{
		if(castTime < 0 || castTime > cooldowns[lvl-1])
			throw new IllegalArgumentException();

		castTimes[lvl-1] = castTime;
	}
	
	public void setManaCost(double manaCost) 
	{
		for(int lvl = 1; lvl <= maxLvl; lvl++)
			setManaCost(manaCost, lvl);
	}
	
	public void setManaCost(double manaCost, int lvl) {
		manaCosts[lvl-1] = manaCost;
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}	

	public boolean isCasting() {
		return casting;
	}
	
	public boolean onCooldown() {
		return elapsedSinceCast < cooldowns[lvl - 1];
	}

	public boolean canBeCast()
	{
		double mana = stats.getMana();
		
		return enabled && elapsedSinceCast >= cooldowns[lvl - 1] &&
			   mana >= manaCosts[lvl - 1];
	}

	protected boolean canBeActivated()
	{
		return casting && 
			   elapsedSinceCast >= castTimes[lvl-1];	
	}
	
	public long getCastTime() {
		return castTimes[lvl-1];
	}
	
	public long getCastTime(int lvl) {
		return castTimes[lvl-1];
	}
	
	public long getElapsedSinceCast() {
		return elapsedSinceCast;
	}
	
	public double getManaCost() {
		return manaCosts[lvl-1];
	}
	
	public double getManaCost(int lvl) {
		return manaCosts[lvl-1];
	}
	
	public RangeIndicator getRangeIndicator() {
		return indicator;
	}
	
	public String toString()
	{
		String str = super.toString();
		
		str += "\ncooldown: " + cooldowns[lvl-1] +
			   "\ncast time: " + castTimes[lvl-1] +
			   "\nelapsed since cast: " + elapsedSinceCast +
			   "\ncasting: " + casting +
			   "\nenabled: " + enabled;
		
		return str;
	}
}
