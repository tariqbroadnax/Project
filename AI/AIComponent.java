package AI;

import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

import EntityComponent.EntityComponent;
import Game.Entity;
import Game.SceneResources;
import Utilities.EntityUtilities;

public class AIComponent extends EntityComponent
{
	private Collection<Behaviour> behaviours;
	
	private double viewRadius;
	
	public AIComponent()
	{
		behaviours = new LinkedList<Behaviour>();

		viewRadius = 50;
	}
	
	public AIComponent(AIComponent comp)
	{
		behaviours = new LinkedList<Behaviour>();
		
		viewRadius = comp.viewRadius;
		
		for(Behaviour behaviour : comp.behaviours)
			addBehaviours((Behaviour)behaviour.clone());
	}
	
	@Override
	public void update(Duration delta){}
	
	public void applyBehaviours(SceneResources resources)
	{
		Collection<Entity> viewableEntities =
				resources.tileMap
						 .objsWithinDistance(
								 EntityUtilities.getLoc(parent),
								 viewRadius);
				
		for(Behaviour behaviour : behaviours)
			if(behaviour.isValid(viewableEntities))
				behaviour.apply();
			else if(behaviour.wasValid())
				behaviour.revert();
	}
	
	public void addBehaviours(Behaviour... behaviours)
	{
		for(int i = 0; i < behaviours.length; i++)
		{
			this.behaviours.add(behaviours[i]);
			behaviours[i].setTarget(parent);
		}
	}
	
	public void setParent(Entity parent)
	{
		super.setParent(parent);
		
		for(Behaviour behaviour : behaviours)
			behaviour.setTarget(parent);
	}

	@Override
	protected EntityComponent _clone()
	{
		return new AIComponent(this);
	}

}
