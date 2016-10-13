package Modifiers;

import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

import Game.Entity;

public abstract class TemporaryModifier extends Modifier
{
	private Map<Entity, Duration> elapsedSinceUseMap;
	
	private Duration lifetime;
	
	private Queue<Entity> removeQueue;
	
	public TemporaryModifier()
	{
		super();
		
		elapsedSinceUseMap = new HashMap<Entity, Duration>();
	
		lifetime = Duration.ofSeconds(1);
	
		removeQueue = new LinkedList<Entity>();
	}
	
	public void update(Duration delta)
	{
		super.update(delta);
	
		updateAllElapsedSinceUse(delta);
		removeModifierFromEntities();
	}
	
	private void updateAllElapsedSinceUse(Duration delta)
	{
		Duration elapsedSinceUse;
		
		for(Entity e : elapsedSinceUseMap.keySet())
		{
			elapsedSinceUse = 
					elapsedSinceUseMap.get(e).plus(delta);
			
			if(elapsedSinceUse.compareTo(lifetime) >= 0)
				removeQueue.add(e);
			else
				elapsedSinceUseMap.put(e, elapsedSinceUse);
		}
	}
	
	private void removeModifierFromEntities()
	{
		Entity e;
		while(!removeQueue.isEmpty())
		{
			e = removeQueue.poll();
			removeFrom(e);
			elapsedSinceUseMap.remove(e);
		}
	}
	
	protected void _applyTo(Entity e)
	{
		elapsedSinceUseMap.put(e, Duration.ZERO);
		__applyTo(e);	
	}
	
	protected abstract void __applyTo(Entity e);
	
	protected abstract void removeFrom(Entity e);
	
	public void setLifetime(Duration lifetime)
	{
		this.lifetime = lifetime;
	}
	
	public Duration getLifetime()
	{
		return lifetime;
	}
		
}
