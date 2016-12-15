package Stat;

import java.util.TreeSet;

import Modifiers.Modifier;

public class Stats
{
	private double maxHealth, health, speed;
	
	private TreeSet<Modifier> healthMods;
	
	public Stats()
	{
		health = maxHealth = 100;
		speed = 50;
		
		healthMods = new TreeSet<Modifier>();
	}
	
	public void setBaseSpeed(double speed) 
	{
		if(speed < 0) 
			throw new IllegalArgumentException();
	
		this.speed = speed;
	}
	
	public void addHealth(double val)
	{
		if(val < 0)
			throw new IllegalArgumentException();
		
		double maxHealth = getMaxHealth();
		health += val;
		health = health > maxHealth ? maxHealth : health;
	}
	
	public void applyDamage(double val)
	{
		if(val < 0)
			throw new IllegalArgumentException();
		
		health -= val;
		health = health < 0 ? 0 : health;
	}
	
	public void addHealthMod(Modifier mod) {
		healthMods.add(mod);
	}
	
	public void removeHealthMod(Modifier mod) 
	{
		healthMods.remove(mod);
	
		double maxHealth = getMaxHealth();
		health = health > maxHealth ? maxHealth : health;
	}
	
	public double getSpeed() {
		return speed;
	}
	
	public double getHealth() {
		return health;
	}

	public double getMaxHealth() 
	{
		double maxHealth = this.maxHealth;
		
		for(Modifier mod : healthMods)
			health = mod.modify(health);
		
		return maxHealth;
	}
	
	public double getBaseMaxHealth() {
		return maxHealth;
	}
}
