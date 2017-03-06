package Ability;

import java.io.Serializable;

import Entity.Entity;
import Game.Updatable;

public abstract class Ability
	implements Serializable, Updatable, Cloneable
{	
	private String name;
	
	protected int lvl, maxLvl;

	protected Entity src;

	public Ability()
	{
		name = "unnamed";
		lvl = 1;
		maxLvl = 5;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setLevel(int lvl) {
		this.lvl = lvl;
	}
	
	public void setMaxLevel(int maxLvl) {
		this.maxLvl = maxLvl;
	}
	
	public void levelUp() {
		lvl++;
	}
	
	public void setSrc(Entity src) {
		this.src = src;
	}
	
	public String getName() {
		return name;
	}
	
	public int getLevel() {
		return lvl;
	}
	
	public int getMaxLevel() {
		return maxLvl;
	}
	
	public Entity getSrc() {
		return src;
	}
	
	public abstract Object clone();
}
