package EntityComponent;

import java.io.Serializable;

public interface CollisionResponse extends Cloneable, Serializable
{
	public void collisionOccurred(CollisionEvent e);	
	
	public Object clone();
}
