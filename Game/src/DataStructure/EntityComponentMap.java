package DataStructure;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import EntityComponent.EntityComponent;
import Game.Entity;

public class EntityComponentMap
	implements Serializable
{
	private HashMap< Class<? extends EntityComponent>,
					 Collection<Entity> > compMap;
	
	public EntityComponentMap()
	{
		compMap = new HashMap<Class<? extends EntityComponent>,
							  Collection<Entity>>();
	}
	
	public void addEntity(Entity e)
	{
		for(Class<? extends EntityComponent> compClass : e.getComponentClasses())
		{
			if(!compMap.containsKey(compClass))
				compMap.put(compClass, new LinkedList<Entity>());
			
			compMap.get(compClass).add(e);
		}
	}
	
	public void removeEntity(Entity e)
	{
		for(Class<? extends EntityComponent> compClass : e.getComponentClasses())
		{
			compMap.get(compClass)
				   .remove(e);
		}		
	}
	
	public Collection<Entity> getEntitiesWithComponent(
			Class<? extends EntityComponent> compClass)
	{
		return compMap.get(compClass);
	}
	
	public String toString()
	{
		String str = "Entity Component Map -";
		
		for(Class<? extends EntityComponent> compClass : compMap.keySet())
		{
			str += "\n" + compClass.toGenericString();
			
			for(Entity e : compMap.get(compClass))
				str += "\n\t" + e.toString();
		}
		
		return str;
	}
}
