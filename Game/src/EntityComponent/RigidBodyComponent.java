package EntityComponent;

import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;
import java.time.Duration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import Entity.Entity;
import Game.RigidBodyIndicator;
import Utilities.Pack;

public class RigidBodyComponent extends EntityComponent
{
	private RigidBody body;
		
	private Map<CollisionResponse, List<CollisionFilter>> map;
	
	private RigidBodyIndicator indicator;
	
	public RigidBodyComponent()
	{
		super();
		
		body = new RigidBody();
		
		map = new HashMap<CollisionResponse,
						  List<CollisionFilter>>();
	
		indicator = new RigidBodyIndicator();
		
		indicator.setRigidBody(body);
	}
	
	public RigidBodyComponent(RigidBodyComponent comp)
	{
		super();
		
		body = (RigidBody) comp.body.clone();
		
		map = new HashMap<CollisionResponse,
				  List<CollisionFilter>>();

		for(CollisionResponse response : comp.map.keySet())
		{
			List<CollisionFilter> filters = comp.map.get(response),
								  clones = new LinkedList<CollisionFilter>();
			
			for(CollisionFilter filter : filters)
				clones.add((CollisionFilter)filter.clone());
		}
		
		indicator = new RigidBodyIndicator();
		
		indicator.setRigidBody(body);
	}
	
	@Override
	public void update(Duration delta)
	{
		if(enabled)
		{
			Point2D.Double parentLoc = parent.getLoc();
			
			body.updateLimbs(parentLoc);
		}
	}
	
	public void add(CollisionResponse response,
					CollisionFilter... filters)
	{
		map.put(response, new LinkedList<CollisionFilter>());
	
		for(CollisionFilter filter : filters)
			put(response, filter);
	}
	
	public void remove(CollisionResponse response)
	{
		map.remove(response);
	}
	
	public void put(CollisionResponse response,
					CollisionFilter filter)
	{
		map.get(response)
		   .add(filter);
	}
	
	public void checkForAndHandleCollision(RigidBodyComponent comp)
	{
		if(!enabled) return;
	
		System.out.println("checking 4 collision");

		RigidBody otherBody = comp.getRigidBody();
		
		Pack<RectangularShape, RectangularShape> collision =
				body.collidesWith(otherBody);
	
		if(collision != null)
			handleCollision(collision, comp);
	}
	
	private void handleCollision(
			Pack<RectangularShape, RectangularShape> collision,
			RigidBodyComponent collided)
	{		
		System.out.println("collision!!");
		
		CollisionEvent e = new CollisionEvent(
				parent, collided.parent,
				collision.head, collision.tail);
		
		Entity target = collided.parent;
		
		for(CollisionResponse response : map.keySet())
		{
			List<CollisionFilter> filters = 
					map.get(response);
			
			boolean valid = true;
			
			for(CollisionFilter filter : filters)
			{
				if(!filter.validCollision(target))
				{
					valid = false;
					break;
				}
			}

			if(valid)
			{
				response.collisionOccurred(e);
			}
		}
	}

	public void setParent(Entity parent) 
	{
		super.setParent(parent);
		
//		parent.get(GraphicsComponent.class)
//			  .getDecorations()
//			  .addLayer(indicator);
	}
	
	public void setRigidBody(RigidBody body) 
	{
		this.body = body;
		indicator.setRigidBody(body);
	}

	public RigidBody getRigidBody() {
		return body;
	}
	
	@Override
	protected EntityComponent _clone() {
		return new RigidBodyComponent(this);
	}
}
