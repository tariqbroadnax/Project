package EntityComponent;

import java.awt.geom.RectangularShape;
import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import Game.Entity;
import Utilities.Pack;

public class RigidBodyComponent extends EntityComponent
{
	// this class should NEVER be sent over network
	// clients and server should have copy
	
	private transient RigidBody body;
	
	private transient BodyType type;
	
	private transient 
		Map<BodyType, Collection<CollisionResponse>> 
			responseMap;
	
	private transient LinkedList<Entity> collisions;
	private transient int maxCollisionsStored;
	
	public RigidBodyComponent()
	{
		super();
		
		body = new RigidBody();
		
		type = BodyType.BEING;
		
		responseMap = new HashMap<BodyType, Collection<CollisionResponse>>();
	
		collisions = new LinkedList<Entity>();
		maxCollisionsStored = 5;
	}
	
	private void initTransient()
	{
		body = new RigidBody();
		
		type = BodyType.BEING;
		
		responseMap = new HashMap<BodyType, Collection<CollisionResponse>>();
	
		collisions = new LinkedList<Entity>();
		maxCollisionsStored = 5;
	}

	public RigidBodyComponent(RigidBodyComponent comp)
	{
		super();
		
		body = new RigidBody(comp.body);
		
		type = comp.type;
		
		responseMap = new HashMap<BodyType, Collection<CollisionResponse>>();

		collisions = new LinkedList<Entity>();
		maxCollisionsStored = comp.maxCollisionsStored;
		
		for(BodyType type : BodyType.values())
			if(comp.responseMap.containsKey(type))
				addCollisionResponses(type, comp.responseMap.get(type));
	}
	
	public void addCollisionResponses(BodyType type, CollisionResponse... responses)
	{
		if(responseMap.get(type) == null)
			responseMap.put(type, new LinkedList<CollisionResponse>());
		
		for(CollisionResponse response : responses)
			responseMap.get(type).add(response);
	}
	
	public void addCollisionResponses(BodyType type,
			Collection<CollisionResponse> responses)
	{
		if(responses == null) return;
		
		if(responseMap.get(type) == null)
			responseMap.put(type, new LinkedList<CollisionResponse>());
		
		responseMap.get(type)
				   .addAll(responses);
	}
	
	@Override
	public void update(Duration delta)
	{
		if(enabled)
			updateLoc();
	}
	
	public void updateLoc()
	{
		body.setLoc(parent.getLoc());			
	}
	
	public void checkForAndHandleCollision(RigidBodyComponent bodyComp)
	{
		if(!enabled) return;
		
		Pack<RectangularShape, RectangularShape> collision;
				
		collision = this.body.collidesWith(bodyComp.getRigidBody());
	
		if(collision != null)
			handleCollision(collision, bodyComp);	
	}
	
	private void handleCollision(
			Pack<RectangularShape, RectangularShape> collision,
			RigidBodyComponent collided)
	{
		Collection<CollisionResponse> responses = 
				responseMap.get(collided.getBodyType());
		
		if(collisions.size() > maxCollisionsStored)
			collisions.removeFirst();
		
		collisions.add(collided.parent);
		
		if(responses != null)
		{
			CollisionEvent e = new CollisionEvent(
					parent, collided.parent,
					collision.head, collision.tail);
		
			for(CollisionResponse response : responses)
			{
				response.collisionOccurred(e);
			}
		}
		/*
		else
			System.out.println("COLLISION"); */
	}
	
	private void readObject(java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		initTransient();
		
	}
	
	public void setBodyType(BodyType type)
	{
		this.type = type;
	}

	public RigidBody getRigidBody()
	{
		return body;
	}
	
	public BodyType getBodyType()
	{
		return type;
	}

	public LinkedList<Entity> getCollisions()
	{
		return collisions;
	}
	
	@Override
	protected EntityComponent _clone()
	{
		return new RigidBodyComponent(this);
	}
	
	public String toString()
	{
		String str = super.toString();
		
		str += "\ntype: " + type;
		str += "\nbody: \n" + body;
		str += "\nreponse map: ";
		for(BodyType type : responseMap.keySet())
		{
			str += '\n' + type.toString();
			str += "\nresponses: ";
			for(CollisionResponse response : responseMap.get(type))
				str += '\n' + response.toString();
		}
		
		return str;
	}
}
