package EntityComponent;

import java.io.Serializable;

import Game.Entity;
import Game.Updatable;

public abstract class EntityComponent 
	implements Updatable, Cloneable, Serializable
{
	protected Entity parent;
	
	protected boolean enabled;
	
	public EntityComponent()
	{
		enabled = true;
	}
	
	public void setParent(Entity parent)
	{
		this.parent = parent;
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}

	public boolean isEnabled()
	{
		return enabled;
	}
	
	public final Entity getParent()
	{
		return parent;
	}
	
	public Object clone()
	{
		return _clone();
	}
	
	public void dispose(){}
	
	protected abstract EntityComponent _clone();
	
}
