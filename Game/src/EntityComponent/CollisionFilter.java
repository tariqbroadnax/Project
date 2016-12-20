package EntityComponent;

import Entity.Entity;

public interface CollisionFilter extends Cloneable
{
	public boolean validCollision(Entity target);
	
	public default Object clone() {
		return this.clone();
	}
}
