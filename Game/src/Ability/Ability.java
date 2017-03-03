package Ability;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import Entity.Entity;
import Game.Updatable;
import Modifiers.Effect;

public abstract class Ability
	implements Serializable, Updatable, Cloneable
{	
	protected Entity src;
	
	public Ability()
	{
	}
	
	public void setSrc(Entity src) {
		this.src = src;
	}
	
	public Entity getSrc() {
		return src;
	}
	
	public abstract Object clone();
}
