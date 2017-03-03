package EntityComponent;

import java.awt.geom.Point2D;
import java.awt.geom.RectangularShape;
import java.time.Duration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import CollisionResponses.Repel;
import Entity.Entity;
import Game.RigidBodyIndicator;
import Utilities.Pack;

public class RigidBodyComponent extends EntityComponent
{
	private RigidBody body;
		
	private Set<CollisionResponse> responses;
	
	private RigidBodyIndicator indicator;
	
	private BodyType type;
	
	public RigidBodyComponent()
	{
		super();
				
		body = new RigidBody();
		
		responses = new HashSet<CollisionResponse>();
		
		type = BodyType.DYNAMIC;

		indicator = new RigidBodyIndicator();
		
		indicator.setRigidBody(body);
	}
	
	public RigidBodyComponent(RigidBodyComponent comp)
	{
		super();
		
		body = (RigidBody) comp.body.clone();
		
		responses = new HashSet<CollisionResponse>();

		for(CollisionResponse response : comp.responses)
		{
			responses.add((CollisionResponse) response.clone());
		}
		
		type = comp.type;
		
		indicator = new RigidBodyIndicator();
		
		indicator.setRigidBody(body);
	}
	
	@Override
	public void update(Duration delta)
	{
		if(enabled)
			updateLimbs();
	}
	
	public void updateLimbs()
	{
		Point2D.Double parentLoc = parent.getLoc();
		
		body.updateLimbs(parentLoc);
	}
	
	public void add(CollisionResponse response)
	{
		responses.add(response);
	}
	
	public void remove(CollisionResponse response)
	{
		responses.remove(response);
	}

	
	public void checkForAndHandleCollision(RigidBodyComponent comp)
	{
		if(!enabled) return;
	
		RigidBody otherBody = comp.getRigidBody();
		
		Pack<RectangularShape, RectangularShape> collision =
				body.collidesWith(otherBody);
	
		if(collision != null)
			handleCollision(collision, comp);
	}

	private void resolveCollision(
			RigidBodyComponent collided, CollisionEvent e)
	{
		if(type == BodyType.NONE || 
		   collided.type == BodyType.NONE ||
		   collided.type == BodyType.STATIC)
			return;
				
		new Repel().collisionOccurred(e);
	}
	
	private void handleCollision(
			Pack<RectangularShape, RectangularShape> collision,
			RigidBodyComponent collided)
	{		
		CollisionEvent e = new CollisionEvent(
				parent, collided.parent,
				collision.head, collision.tail);
				
		resolveCollision(collided, e);
	
		for(CollisionResponse response : responses)
			response.collisionOccurred(e);
		
		for(CollisionResponse response : collided.responses)
			response.collisionOccurred(e);
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
	
	public void setBodyType(BodyType type) {
		this.type = type;
	}

	public RigidBody getRigidBody() {
		return body;
	}
	
	public BodyType getBodyType() {
		return type;
	}
	
	@Override
	protected EntityComponent _clone() {
		return new RigidBodyComponent(this);
	}
}
