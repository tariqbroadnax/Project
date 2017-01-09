package Entity;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import EntityComponent.EntityComponent;
import Game.Scene;

public class Entity
	implements Serializable, Cloneable
{		
	private Scene sceneLoc;
	
	protected Point2D.Double loc;

	private Map<Class<? extends EntityComponent>,
				EntityComponent> comps;
	
	public Entity()
	{		
		comps = new HashMap<Class<? extends EntityComponent>,
							EntityComponent>();
				
		loc = new Point2D.Double();
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
		{
			//System.out.println(comp.getClass()); // FIXME DELETEME
			add((EntityComponent)comp.clone());
		}
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
		comps.remove(compClass);
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
	}
	
	public void move(double xOff, double yOff)
	{
		setLoc(loc.x + xOff, loc.y + yOff);
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
}
