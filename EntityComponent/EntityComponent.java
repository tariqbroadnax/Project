package EntityComponent;

import java.io.Serializable;
import java.util.Observable;
import java.util.Observer;

import Game.Entity;
import Game.Updatable;

public abstract class EntityComponent extends Observable
	implements Updatable, Cloneable, Serializable,
			   Observer
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
		
		setChanged();
		notifyObservers();
	}
	
	public boolean containsParent()
	{
		return parent != null;
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
		
		setChanged();
		notifyObservers();
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
	
	@Override
	public void update(Observable o, Object src) 
	{
		setChanged();
		notifyObservers();		
	}
	
}
