package EntityComponent;

import java.awt.geom.RectangularShape;

import Entity.Entity;

public class CollisionEvent
{
	public final Entity collider,
						collided;
	
	public final RectangularShape colliderComp,
								  collidedComp;

	public CollisionEvent(
			Entity collider, Entity collided,
			RectangularShape colliderComp,
			RectangularShape collidedComp)
	{
		this.collider = collider;
		this.collided = collided;
	
		this.colliderComp = colliderComp;
		this.collidedComp = collidedComp;
	}
	
	
}
