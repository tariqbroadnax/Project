package EntityManager;

import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import Entity.Entity;
import Entity.Fire;
import EntityComponent.RigidBody;
import EntityComponent.RigidBodyComponent;
import Game.Scene;
import Game.Updatable;
import Maths.Circle2D;

public class CollisionManager implements Updatable
{	
	private Scene scene;
	
	public CollisionManager(Scene scene)
	{
		this.scene = scene;
	}

	@Override
	public void update(Duration delta)
	{
		handleCollisions();
	}
	
	public List<Entity> collisions(Circle2D.Double circ)
	{
		List<Entity> collisions = 
				new LinkedList<Entity>();
		
		List<Entity> es = scene.entitiesWithComponent(
				 RigidBodyComponent.class);
		
		for(Entity entity : es)
		{
		
			RigidBody body = entity.get(RigidBodyComponent.class)
								   .getRigidBody();
			
			//if(entity instanceof Fire)
				//System.out.println("here" + " " + body.collidesWith(circ) != null);
			
			if(body.collidesWith(circ) != null)
				collisions.add(entity);
		}
		
		return collisions;
	}
	
	private void handleCollisions()
	{
		List<Entity> es = scene.entitiesWithComponent(
								 RigidBodyComponent.class);
	
		Collection<RigidBodyComponent> components =
				new LinkedList<RigidBodyComponent>();
	
		if(es == null) return;
		
		for(Entity e : es)
			components.add(e.get(RigidBodyComponent.class));
			
		for(RigidBodyComponent comp : components)
			for(RigidBodyComponent comp2 : components)
				if(comp != comp2)
					comp.checkForAndHandleCollision(comp2);
	}
	
}
