package Stat;

import java.time.Duration;
import java.util.TreeSet;

import Game.Updatable;
import Modifiers.Modifier;

public class Stats implements Updatable
{
	private int level;
	
	private int exp, expNextLvl;
	
	private int gold;
	
	private double maxHealth, health, healthRegen;
	
	private double speed;
	
	private double critRate;
	
	private TreeSet<Modifier> expMods, healthMods, speedMods;
	
	public Stats()
	{
		level = 1;
		
		exp = 0; expNextLvl = 100;
		
		health = maxHealth = 100;
		
		healthRegen = 5;
		
		speed = 50;
		
		critRate = .50;
		
		expMods = new TreeSet<Modifier>();
		healthMods = new TreeSet<Modifier>();
		speedMods = new TreeSet<Modifier>();
	}
	
	public void update(Duration delta)
	{
		double t = delta.toMillis() / 1000.0;
		
		health += healthRegen * t;
	}
	
	public void heal(double val)
	{
		if(val < 0)
			throw new IllegalArgumentException();
		
		double maxHealth = getMaxHealth();
		health += val;
		health = health > maxHealth ? maxHealth : health;
	}
	
	public void damage(double val)
	{
		if(val < 0)
			throw new IllegalArgumentException();
		
		health -= val;
		health = health < 0 ? 0 : health;
	}
	
	public void addExp(int exp)
	{
		for(Modifier mod : expMods)
			exp = (int) mod.modify(exp);
		
		this.exp += exp;
		
		while(exp > expNextLvl)
		{
			exp -= expNextLvl;
			level++;
			expNextLvl *= 2;
		}
	}
	
	public void setBaseMaxHealth(double maxHealth) {
		this.maxHealth = maxHealth;
	}
	
	public void setBaseSpeed(double speed) {
		this.speed = speed;
	}
	
	public void addGold(int gold) {
		this.gold += gold;
	}
	
	public void removeGold(int gold) {
		this.gold -= gold;
	}
	
	public double getHealth() {
		return health;
	}

	public double getMaxHealth() 
	{
		double maxHealth = this.maxHealth;
		
		for(Modifier mod : healthMods)
			maxHealth = mod.modify(maxHealth);
		
		return maxHealth;
	}
	
	public double getBaseMaxHealth() {
		return maxHealth;
	}
	
	public double getBaseSpeed() {
		return speed;
	}
	
	public double getSpeed() 
	{
		double speed = this.speed;
		
		for(Modifier mod : speedMods)
			speed = mod.modify(speed);
		
		return speed;
	}
	
	public int getLevel() {
		return level;
	}
	
	public int getExp() {
		return exp;
	}
	
	public int getExpNextLvl() {
		return expNextLvl;
	}
	
	public int getGold() {
		return gold;
	}
	
	public double getCritRate() {
		return critRate;
	}
	
	public double getBlockRate() {
		return 0;
	}
	
	public double getDodgeRate() {
		return 0;
	}
	
	public void addExpModifier(Modifier mod) {
		expMods.add(mod);
	}
	
	public void removeExpModifier(Modifier mod) {
		expMods.remove(mod);
	}
	
	public void addHealthModifier(Modifier mod) {
		healthMods.add(mod);
	}
	
	public void removeHealthModifier(Modifier mod) {
		healthMods.remove(mod);
	}
	
	public void addSpeedModifier(Modifier mod) {
		speedMods.add(mod);
	}
	
	public void removeSpeedModifier(Modifier mod) {
		speedMods.remove(mod);
	}
}
