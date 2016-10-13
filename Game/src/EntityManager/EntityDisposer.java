package EntityManager;

import java.time.Duration;
import java.util.Collection;

import EntityComponent.LifetimeComponent;
import Game.Entity;
import Game.SceneResources;

public class EntityDisposer extends EntityManager
{
	public EntityDisposer(SceneResources resources)
	{
		super(resources);
	}

	@Override
	public void update(Duration delta)
	{		
		disposeOfEntities();
	}
	
	public void disposeOfEntities()
	{
		Collection<Entity> es =
				resources.componentMap
						 .getEntitiesWithComponent(
								 LifetimeComponent.class);
		
		if(es == null) return;
		
		for(Entity e : es)
			e.get(LifetimeComponent.class)
			 .checkAndRemoveParent(resources);	
	}
}
