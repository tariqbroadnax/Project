package Modifiers;

import java.time.Duration;

import Game.Entity;
import Game.Updatable;

public abstract class Modifier 
	implements Updatable, Cloneable
{
	protected Duration lifetime;
	private Duration elapsed;
	
	protected Entity src, target;
	
	public Modifier()
	{
		lifetime = Duration.ofMillis(500);
	}
	
	public Modifier(Modifier mod)
	{
		this();
		
		lifetime = mod.lifetime;
	}
	
	public void update(Duration delta)
	{		
		elapsed = elapsed.plus(delta);
	}
	
	protected abstract void apply();
	
	public void revert(){}
	
	public boolean isOver()
	{
		return elapsed.compareTo(lifetime) >= 0;
	}
	
	public void setTarget(Entity target)
	{
		if(this.target != null) revert();
		
		this.target = target;
		
		apply();
		
		elapsed = Duration.ZERO;
	}
	
	public void setSource(Entity src)
	{
		this.src = src;
	}
	
	public void setLifetime(Duration lifetime)
	{
		this.lifetime = lifetime;
	}
	
	public Object clone()
	{
		return _clone();
	}
	
	protected abstract Object _clone();
}
