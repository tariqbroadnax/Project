package Behavior;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Entity.Entity;
import EntityComponent.EntityComponent;

public class BehaviorComponent extends EntityComponent
{
	private List<Behavior> behaviors;
	
	private boolean started = false;
	
	public BehaviorComponent() 
	{
		behaviors = new ArrayList<Behavior>();		
	}
	
	public BehaviorComponent(BehaviorComponent comp)
	{
		this();
//		
//		for(Behavior beh : comp.behaviors)
//			behaviors.add((Behavior)beh.clone());
	}
	
	public void start()
	{
		started = true;
		
		for(Behavior beh : behaviors)
		{
			beh.setSrc(parent);
			beh.start();
		}
	}
	
	@Override
	public void update(Duration delta) 
	{	
		for(int i = 0; i < behaviors.size(); i++)
		{
			Behavior b = behaviors.get(i);
			
			b.update(delta);		
		}
	}
	
	public void addBehavior(Behavior behavior) 
	{
		behaviors.add(behavior);
		
		if(started)
		{
			behavior.setSrc(parent);
			behavior.start();
		}
	}
	
	public void removeBehavior(Behavior behavior) 
	{
		
	}
	
	public <E extends Behavior> E getBehavior(
			Class<E> c)
	{
		for(Behavior beh : behaviors)
			if(beh.getClass().equals(c))
				return (E) beh;
		return null;
	}
	
	public Collection<Behavior> getBehaviors() {
		return behaviors;
	}

	@Override
	protected EntityComponent _clone() {
		return new BehaviorComponent(this);
	}
}
