package Behaviour;

import java.time.Duration;

import Game.Entity;
import Game.Updatable;

public abstract class Behaviour 
	implements Updatable
{
	protected Entity src;
		
	public Behaviour(){}
	
	public Behaviour(Entity src){
		this.src = src;
	}

	public void setSrc(Entity src) {
		this.src = src;
	}
	
}
