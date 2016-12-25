package Behaviour;

import java.io.Serializable;

import Entity.Entity;
import Game.Updatable;

public abstract class Behaviour 
	implements Updatable, Cloneable, Serializable
{
	protected Entity src;
		
	public Behaviour(){}
	
	public Behaviour(Entity src){
		this.src = src;
	}

	public void setSrc(Entity src) {
		this.src = src;
	}
	
	public abstract Object clone();
	
}
