package CollisionResponses;

import java.awt.geom.RectangularShape;
import java.time.Duration;
import java.util.Arrays;

import EntityComponent.CollisionEvent;
import EntityComponent.CollisionResponse;
import EntityComponent.RigidBodyComponent;
import Game.Entity;
import Graphic.Vector2D;
import Utilities.EntityUtilities;
import Utilities.ShapeUtilities;

public class BodyPusher implements CollisionResponse
{
	private Vector2D.Double upShift, downShift,
							leftShift, rightShift,
							smallestShift;

	@Override
	public void collisionOccurred(CollisionEvent e)
	{
		findShifts(e.colliderComp, e.collidedComp);
		findSmallestShift();
		moveCollider(e.collider);
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

	private void moveCollider(Entity collider)
	{
		smallestShift.move(collider.getLoc());
		
		collider.get(RigidBodyComponent.class)
				.updateLoc();
	}
	
	private Vector2D.Double findUpShift(
			RectangularShape colliderComp,
			RectangularShape collidedComp)
	{	
		return new Vector2D.Double(0, 
				collidedComp.getMinY() -
				colliderComp.getMaxY());
	}
	
	private Vector2D.Double findDownShift(
			RectangularShape colliderComp,
			RectangularShape collidedComp)
	{
		return new Vector2D.Double(0, 
				collidedComp.getMaxY() -
				colliderComp.getMinY());
	}
	
	private Vector2D.Double findLeftShift(
			RectangularShape colliderComp,
			RectangularShape collidedComp)
	{
		return new Vector2D.Double( 
				collidedComp.getMinX() -
				colliderComp.getMaxX(), 0);
	}
	
	private Vector2D.Double findRightShift(
			RectangularShape colliderComp,
			RectangularShape collidedComp)
	{
		return new Vector2D.Double( 
				collidedComp.getMaxX() -
				colliderComp.getMinX(), 0);
	}
}
