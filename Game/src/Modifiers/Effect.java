package Modifiers;

import Entity.Entity;
import Game.Updatable;

public abstract class Effect 
	implements Updatable
{
	protected Entity src, target;
			
	public abstract void start();
	
	public abstract void stop();
	
	public void setSource(Entity src) {
		this.src = src;
	}
	
	public void setTarget(Entity target) {
		this.target = target;
	}
	
	public abstract boolean canBeApplied(Entity target);
	
	public abstract boolean isFinished();
	
	public Object clone()
	{
		Effect effect = _clone();
		
		effect.setSource(src);
		
		return effect;
	}
	
	protected abstract Effect _clone();
}
