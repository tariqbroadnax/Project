package EntityComponent;

import java.io.Serializable;

import Entity.Entity;
import Game.Updatable;

public abstract class EntityComponent
	implements Updatable, Cloneable, Serializable
{
	protected Entity parent;
	
	protected boolean enabled;
	
	public EntityComponent()
	{
		parent = null;
		
		enabled = true;
	}
	
	public void setParent(Entity parent)
	{
		if(parent == null)
			throw new NullPointerException();
		
		this.parent = parent;
	}
	
	public boolean containsParent()
	{
		return parent != null;
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
