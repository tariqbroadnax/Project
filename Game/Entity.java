package Game;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

import EntityComponent.EntityComponent;

public class Entity extends Observable 
	implements Serializable, Cloneable, Observer
{		
	private Scene sceneLoc;
	
	protected Point2D.Double loc;

	private Map<Class<? extends EntityComponent>,
				EntityComponent> comps;
				
	private Collection<EntityListener> listeners;
	
	public Entity()
	{		
		comps = new HashMap<Class<? extends EntityComponent>,
							EntityComponent>();
				
		loc = new Point2D.Double();
	
		listeners = new ArrayList<EntityListener>();
	}
	
	public Entity(Entity e)
	{		
		this();
		
		copy(e);
	}

	public void update(Duration delta)
	{		
		updateComponents(delta);
	}
	
	public void updateComponents(Duration delta)
	{
		for(EntityComponent comp : comps.values())
			comp.update(delta);
	}
	
	public void copy(Entity model)
	{
		for(EntityComponent comp : model.comps.values()) 
			add((EntityComponent)comp.clone());
		
		setLoc(model.loc);
	}

	public void add(EntityComponent comp)
	{
		if(comp == null)
			throw new NullPointerException();
	
		else if(comp.containsParent())
			throw new IllegalArgumentException(
					"Component already contains parent");
		else
		{
			Class<? extends EntityComponent> c =
					comp.getClass();
			
			while(c.getSuperclass() != EntityComponent.class)
				c = (Class<? extends EntityComponent>)
					c.getSuperclass();
			
			EntityComponent oldComp = comps.get(c);
			
			comps.put(c, comp);
	
			// !!! IMPORTANT !!!
			comp.setParent(this);
			comp.addObserver(this);
			
			setChanged();
			notifyObservers();
			
			if(oldComp == null)
				notifyListenersOfAddedComponent(comp);
			else
			{
				oldComp.deleteObserver(this);
				notifyListenersOfReplacedComponent(
						oldComp, comp);
			}
		}
	}
	
	public void add(EntityComponent... comps)
	{
		for(EntityComponent comp : comps)
			add(comp);
			
	}
	
	public Collection<Class<? extends EntityComponent>> 
		   getComponentClasses()
	{
		return comps.keySet();
	}
	
	public <E extends EntityComponent> void remove(
			Class<E> compClass)
	{
		if(comps.containsKey(compClass))
		{
			EntityComponent comp = 
					comps.remove(compClass);
			
			comp.deleteObserver(this);
	
			setChanged();
			notifyObservers();
			
			notifyListenersOfRemovedComponent(comp);
		}
	}
	
	private void notifyListenersOfAddedComponent(
			EntityComponent comp)
	{
		for(EntityListener listener : listeners)
			listener.componentAdded(this, comp);
	}
	
	private void notifyListenersOfRemovedComponent(
			EntityComponent comp)
	{
		for(EntityListener listener : listeners)
			listener.componentRemoved(this, comp);
	}
	
	private void notifyListenersOfReplacedComponent(
			EntityComponent oldComp,
			EntityComponent newComp)
	{
		for(EntityListener listener : listeners)
			listener.componentReplaced(
					this, oldComp, newComp);
	}
	
	@SuppressWarnings("unchecked")
	public <E extends EntityComponent> E get(
			Class<E> compClass)
	{
		return (E) comps.get(compClass);
	}
	
	public Collection<EntityComponent> getComponents()
	{
		return comps.values();
	}
	
	public <E extends EntityComponent> boolean contains(
			Class<E> compClass)
	{
		return comps.containsKey(compClass);
	}
	
	public void addEntityListener(EntityListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeEntityListener(EntityListener listener)
	{
		listeners.remove(listener);
	}
	
	public void setLoc(Point2D.Double loc)
	{
		if(loc == null)
			throw new NullPointerException();
		
		setLoc(loc.x, loc.y);	
	}
	
	public void setSceneLoc(Scene sceneLoc) {
		this.sceneLoc = sceneLoc;
	}
	
	public Scene getSceneLoc() {
		return sceneLoc;
	}
	
	public void setLoc(double x, double y)
	{
		loc.x = x;
		loc.y = y;
		
		setChanged();
		notifyObservers();		
	}
	
	public Point2D.Double getLoc()
	{
		return loc;
	}

	public Object clone()
	{
		return new Entity(this);
	}
	
	public String toString()
	{
		String str = super.toString();
	
		str += "\nComponents: ";
		
		for(EntityComponent comp : comps.values())
			str += '\n' + comp.toString() + "\n--------------------";
		
		return str;
	}
	
	public String toString2()
	{
		return super.toString();
	}

	@Override
	public void update(Observable o, Object src) 
	{
		setChanged();
		notifyObservers();
	}
}
