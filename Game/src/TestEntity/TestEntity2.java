package TestEntity;

import java.time.Duration;

import CollisionResponses.ApplyModifiers;
import CollisionResponses.EndLifetime;
import EntityComponent.BodyType;
import EntityComponent.EntityComponent;
import EntityComponent.GraphicsComponent;
import EntityComponent.LifetimeComponent;
import EntityComponent.ModifierComponent;
import EntityComponent.RigidBodyComponent;
import EntityComponent.StatsComponent;
import Game.Entity;
import Graphic.LayeredGraphic;
import Graphic.ShapeGraphic;
import Graphic.Vector2D;
import Maths.Circle2D;
import Modifiers.Damage;
import Modifiers.Root;
import Movement.MovementComponent;

public class TestEntity2 extends Entity
{
	public TestEntity2()
	{
		super();
		
		add(new StatsComponent(),
			createGraphicsComponent(),
			new MovementComponent(), 
			createRigidBodyComponent(),
			createLifetimeComponent(),
			createModifierComponent());
	}
	
	private EntityComponent createGraphicsComponent()
	{
		GraphicsComponent comp = new GraphicsComponent();
		
		LayeredGraphic layerGraph = new LayeredGraphic();
	
		ShapeGraphic shapeGraph = new ShapeGraphic();
		
		shapeGraph.setShape(new Circle2D.Double(0, 0, 5));
		
		layerGraph.addGraphic(shapeGraph, new Vector2D.Double(-2.5, -2.5));
		
		comp.setGraphic(layerGraph);
		
		return comp;
	}
	
	private EntityComponent createRigidBodyComponent()
	{
		RigidBodyComponent comp = new RigidBodyComponent();
		
		comp.getRigidBody()
			.addComponent(
					new Vector2D.Double(-2.5, -2.5),
					new Circle2D.Double(0, 0, 5));
		
		comp.setBodyType(BodyType.ABILITY);
		
		comp.addCollisionResponses(BodyType.STRUCTURE,
				new ApplyModifiers(), new EndLifetime());
		
		return comp;
	}
	
	private EntityComponent createLifetimeComponent()
	{
		LifetimeComponent comp = new LifetimeComponent();
		
		comp.getLifetime()
			.setLength(Duration.ofSeconds(3));
		
		return comp;
	}
	
	private EntityComponent createModifierComponent()
	{
		ModifierComponent comp = new ModifierComponent();
		
		comp.addModifierToDeliver(new Damage(-10, 0), new Root());
		
		return comp;
	}
}
