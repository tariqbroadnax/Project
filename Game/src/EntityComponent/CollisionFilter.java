package EntityComponent;

import Entity.Entity;

public interface CollisionFilter 
{
	public boolean validCollision(Entity target);
}
