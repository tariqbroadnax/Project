package CollisionResponses;

import java.time.Duration;

import EntityComponent.CollisionEvent;
import EntityComponent.CollisionResponse;
import EntityComponent.LifetimeComponent;
import EntityComponent.RigidBodyComponent;
import Game.Entity;

public class EndLifetime implements CollisionResponse
{
	// TODO change name to entity disposer
	
	@Override
	public void collisionOccurred(CollisionEvent e)
	{
		endLifetime(e);
		preventFurtherCollisions(e);
	}
	
	private void endLifetime(CollisionEvent e)
	{
		e.collider
		 .get(LifetimeComponent.class)
		 .getLifetime()
		 .setLength(Duration.ZERO);	
	}
	
	private void preventFurtherCollisions(CollisionEvent e)
	{
		e.collider
		 .get(RigidBodyComponent.class)
		 .setEnabled(false);
	}
	
	public static void execute(Entity e)
	{
		e.get(LifetimeComponent.class)
		 .getLifetime()
		 .setLength(Duration.ZERO);
		
		e.get(RigidBodyComponent.class)
		 .setEnabled(false);			
	}
}
