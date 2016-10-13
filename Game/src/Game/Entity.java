package Game;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import EntityComponent.EntityComponent;
import EntityComponent.GraphicsComponent;
import Movement.MovementComponent;
import Movement.TargetedMovement;

public class Entity implements Serializable,
	Comparable<Entity>, Cloneable
{	
	private static int NO_ENTITY_ID = -1;
	
	protected Point2D.Double loc;

	private Map<Class<? extends EntityComponent>,
				EntityComponent> comps;
	
	private int entityID;
	
	private transient int locSceneID;
	
	private Collection<EntityListener> listeners;
	
	public Entity()
	{		
		comps = new HashMap<Class<? extends EntityComponent>,
							EntityComponent>();
		
		listeners = new LinkedList<EntityListener>();
		
		loc = new Point2D.Double();
		
		entityID = NO_ENTITY_ID;
		
		locSceneID = 0; //FIXME TESTING SCENE
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
		
		entityID = model.entityID;

		setLoc(model.loc);
	}
	
	public void interpolate(Entity e)
	{	
		for(Class<? extends EntityComponent> clas : e.comps.keySet())
		{
			if(!clas.equals(MovementComponent.class))
				add(e.comps.get(clas));
		}
	
		// TODO
		// interpolate graphic too
		// prob best that graphic isn't updated 
		
		TargetedMovement movement =
		(TargetedMovement)
		get(MovementComponent.class)
			.getNormalMovement();
				
		movement.setTarget(e.getLoc());
	}
	
	public void compress()
	{
		comps.keySet().removeIf(c -> !c.equals(GraphicsComponent.class));
	}

	public void add(EntityComponent... comps)
	{
		for(EntityComponent comp : comps)
		{
			Class<? extends EntityComponent> clas = 
					topLevelClass(comp.getClass());
			
			if(this.comps.containsKey(clas))
				this.comps.get(clas).dispose();
			
			this.comps.put(clas, comp);
			
			for(EntityListener listener : listeners)
				listener.componentAdded(this, clas);
			
			// !!! IMPORTANT !!!
			comp.setParent(this);
		}
	}
	
	private Class<? extends EntityComponent> topLevelClass(Class<? extends EntityComponent> clas)
	{
		Class<?> clas2 = clas.getSuperclass();
		
		if(clas2.equals(EntityComponent.class))
			return clas;
		else return topLevelClass(
			(Class<? extends EntityComponent>)clas2);
	}
	
	public Collection<Class<? extends EntityComponent>> getComponentClasses()
	{
		return comps.keySet();
	}
	
	public <E extends EntityComponent> void remove(
			Class<E> compClass)
	{
		if(comps.containsKey(compClass))
		{
			comps.remove(compClass);
			
			for(EntityListener list : listeners)
				list.componentRemoved(this, compClass);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <E extends EntityComponent> E get(
			Class<E> compClass)
	{
		return (E) comps.get(topLevelClass(compClass));
	}
	
	public Collection<EntityComponent> getComponents()
	{
		return comps.values();
	}
	
	public <E extends EntityComponent> boolean contains(Class<E> compClass)
	{
		return comps.containsKey(topLevelClass(compClass));
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
		if(this.loc.x == loc.x &&
		   this.loc.y == loc.y) return;
		
		this.loc.x = loc.x;
		this.loc.y = loc.y;		
	}
	
	public void setEntityID(int entityID)
	{
		this.entityID = entityID;
	}
	
	public void setLocationSceneID(int locSceneID)
	{
		this.locSceneID = locSceneID;
	}
	
	public Point2D.Double getLoc()
	{
		return loc;
	}
	
	public int getEntityID()
	{
		return entityID;
	}
	
	public int getLocationSceneID()
	{
		return locSceneID;
	}
	
	@Override
	public int compareTo(Entity e) 
	{
		return Integer.compare(entityID,
							   e.entityID);
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
