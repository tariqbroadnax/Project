package CollisionResponses;

import java.time.Duration;

import Entity.Entity;
import EntityComponent.CollisionEvent;
import EntityComponent.CollisionResponse;
import EntityComponent.LifetimeComponent;
import EntityComponent.RigidBodyComponent;

public class EndLife
	implements CollisionResponse
{
	@Override
	public void collisionOccurred(CollisionEvent e)
	{
		execute(e.collider);
	}

	public static void execute(Entity e)
	{
		e.get(LifetimeComponent.class)
		 .getLifetime()
		 .end();
		
		e.get(RigidBodyComponent.class)
		 .setEnabled(false);			
	}
}
