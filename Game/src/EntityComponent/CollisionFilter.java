package EntityComponent;

import java.io.Serializable;

import Entity.Entity;

public interface CollisionFilter extends Cloneable, Serializable
{
	public boolean validCollision(Entity target);
	
	public default Object clone() {
		return this.clone();
	}
}
