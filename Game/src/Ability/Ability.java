package Ability;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import Entity.Entity;
import Game.Updatable;
import Modifiers.Effect;

public abstract class Ability
implements Serializable, Updatable
{	
	protected Entity src;
	
	protected List<Effect> effects;
	
	public Ability()
	{
		effects = new LinkedList<Effect>();
	}
	
	public void setSrc(Entity src) {
		this.src = src;
	}

	public void addEffect(Effect effect) { 
		effects.add(effect);
	}
	
	public void removeEffect(Effect effect) {
		effects.remove(effect);
	}
}
