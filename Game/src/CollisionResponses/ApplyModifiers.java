package CollisionResponses;

import EntityComponent.CollisionEvent;
import EntityComponent.CollisionResponse;
import EntityComponent.ModifierComponent;
import Game.Entity;

public class ApplyModifiers implements CollisionResponse
{
	@Override
	public void collisionOccurred(CollisionEvent e)
	{
		execute(e.collider, e.collided);
	}
	
	public static void execute(Entity src, Entity target)
	{
		src
		 .get(ModifierComponent.class)
		 .applyModifiers(target);
	}

}
