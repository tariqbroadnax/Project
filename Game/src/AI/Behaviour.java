package AI;

import java.util.Collection;

import Game.Entity;

public abstract class Behaviour
	implements Cloneable
{
	protected Entity target;
	
	private boolean wasValid;
	
	public Behaviour()
	{
		target = null;
	}
	
	public Behaviour(Entity target)
	{
		this.target = target;
	}
	
	public void setTarget(Entity target)
	{
		this.target = target;
	}
	
	public abstract boolean isValid(Collection<Entity> entities);
	
	public void apply()
	{
		wasValid = true;
		
		_apply();
	}
	
	protected abstract void _apply();
	
	public void revert()
	{
		wasValid = false;
		
		_revert();
	}
	
	protected abstract void _revert();
	
	public boolean wasValid()
	{
		return wasValid;
	}
	
	protected abstract Behaviour _clone();
	
	public Object clone()
	{
		return _clone();
	}
}
