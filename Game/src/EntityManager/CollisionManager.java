package EntityManager;

import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

import EntityComponent.RigidBodyComponent;
import Game.Entity;
import Game.SceneResources;

public class CollisionManager
	extends EntityManager
{	
	public CollisionManager(SceneResources resources)
	{
		super(resources);
	}

	@Override
	public void update(Duration delta)
	{
		handleCollisions();
	}
	
	private void handleCollisions()
	{
		Collection<Entity> es =
				resources.componentMap
						 .getEntitiesWithComponent(
								 RigidBodyComponent.class);
	
		Collection<RigidBodyComponent> components =
				new LinkedList<RigidBodyComponent>();
	
		if(es == null) return;
		
		for(Entity e : es)
			components.add(e.get(RigidBodyComponent.class));
			
		for(RigidBodyComponent comp : components)
			for(RigidBodyComponent comp2 : components)
				if(comp != comp2)
					comp.checkForAndHandleCollision(comp2);
	}
	
}
