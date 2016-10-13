package TestEntity;

import java.awt.geom.Rectangle2D;

import CollisionResponses.ApplyModifiers;
import CollisionResponses.EndLifetime;
import EntityComponent.BodyType;
import EntityComponent.EntityComponent;
import EntityComponent.LifetimeComponent;
import EntityComponent.ModifierComponent;
import EntityComponent.RigidBodyComponent;
import EntityComponent.StatsComponent;
import Game.Entity;
import Graphic.Vector2D;
import Modifiers.Damage;
import Modifiers.Knockback;
import Movement.MovementComponent;

public class BasicAttackEntity extends Entity
{
	public BasicAttackEntity()
	{
		super();
		
		add(new StatsComponent(),
			new MovementComponent(), 
			createRigidBodyComponent(),
			new LifetimeComponent(),
			createModifierComponent());
	}
	
	private EntityComponent createRigidBodyComponent()
	{
		RigidBodyComponent comp = new RigidBodyComponent();
		
		comp.getRigidBody()
			.addComponent(
					new Vector2D.Double(-2.5, -2.5),
					new Rectangle2D.Double(0, 0, 5, 5));
		
		comp.setBodyType(BodyType.ABILITY);
		
		comp.addCollisionResponses(BodyType.BEING,
				new ApplyModifiers(), new EndLifetime());
		
		return comp;
	}
	
	private EntityComponent createModifierComponent()
	{
		ModifierComponent comp = new ModifierComponent();
		
		comp.addModifierToDeliver(new Damage(-1, 0), new Knockback());
		
		return comp;
	}
}
