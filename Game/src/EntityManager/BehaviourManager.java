package EntityManager;

import java.time.Duration;
import java.util.Collection;

import AI.AIComponent;
import Game.Entity;
import Game.SceneResources;

public class BehaviourManager extends EntityManager
{
	public BehaviourManager(SceneResources resources)
	{
		super(resources);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void update(Duration delta)
	{
		Collection<Entity> entities = 
				resources.componentMap
						 .getEntitiesWithComponent(
								 AIComponent.class);
		
		if(entities == null) return;
		
		for(Entity e : entities)
			e.get(AIComponent.class)
			 .applyBehaviours(resources);
	}

}
