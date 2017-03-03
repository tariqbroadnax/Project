package CollisionResponses;

import java.awt.geom.RectangularShape;
import java.util.Arrays;

import Entity.Entity;
import EntityComponent.CollisionEvent;
import EntityComponent.CollisionResponse;
import EntityComponent.RigidBodyComponent;
import Maths.Vector2D;

public class Repel implements CollisionResponse
{
	private Vector2D.Double upShift, downShift,
							leftShift, rightShift,
							smallestShift;

	@Override
	public void collisionOccurred(CollisionEvent e)
	{
		findShifts(e.colliderComp, e.collidedComp);
		findSmallestShift();
		moveCollided(e.collided);
	}
	
	private void findShifts(
			RectangularShape colliderComp,
			RectangularShape collidedComp)
	{
		upShift = findUpShift(colliderComp, collidedComp);
		downShift = findDownShift(colliderComp, collidedComp);
		leftShift = findLeftShift(colliderComp, collidedComp);
		rightShift = findRightShift(colliderComp, collidedComp);
	}
	
	private void findSmallestShift()
	{
		smallestShift = upShift;
		
		for(Vector2D.Double shift : 
			Arrays.asList(downShift, leftShift, rightShift))
		{
			smallestShift = 
					smallestShift.magnitudeSqrd() <=
					shift.magnitudeSqrd() ?
					smallestShift : shift;
		}
	}

	private void moveCollided(Entity collider)
	{
		smallestShift.move(collider.getLoc());
		
		collider.get(RigidBodyComponent.class)
				.updateLimbs();
	}
	
	private Vector2D.Double findUpShift(
			RectangularShape colliderComp,
			RectangularShape collidedComp)
	{	
		return new Vector2D.Double(0, 
				colliderComp.getMinY() -
				collidedComp.getMaxY());
	}
	
	private Vector2D.Double findDownShift(
			RectangularShape colliderComp,
			RectangularShape collidedComp)
	{
		return new Vector2D.Double(0, 
				colliderComp.getMaxY() -
				collidedComp.getMinY());
	}
	
	private Vector2D.Double findLeftShift(
			RectangularShape colliderComp,
			RectangularShape collidedComp)
	{
		return new Vector2D.Double( 
				colliderComp.getMinX() -
				collidedComp.getMaxX(), 0);
	}
	
	private Vector2D.Double findRightShift(
			RectangularShape colliderComp,
			RectangularShape collidedComp)
	{
		return new Vector2D.Double( 
				colliderComp.getMaxX() -
				collidedComp.getMinX(), 0);
	}
	
	public Object clone()
	{
		return new Repel();
	}
}
