package TestEntity;

import java.awt.geom.Rectangle2D;

import AI.AIComponent;
import AI.AssaultBehaviour;
import AI.AttackBehaviour;
import Ability.ActiveAbility;
import AbilityInitiators.ProjectileInitiator;
import EntityComponent.AbilityComponent;
import EntityComponent.BodyType;
import EntityComponent.DropComponent;
import EntityComponent.EntityComponent;
import EntityComponent.GraphicsComponent;
import EntityComponent.LifetimeComponent;
import EntityComponent.ModifierComponent;
import EntityComponent.RigidBodyComponent;
import EntityComponent.SpawnComponent;
import EntityComponent.StatsComponent;
import Game.Entity;
import Graphic.LayeredGraphic;
import Graphic.ShapeGraphic;
import Graphic.Vector2D;
import Item.InstantItem;
import Movement.MovementComponent;
import Movement.TargetedMovement;
import Stat.CoreStatType;

public class TestEntity3 extends Entity
{
	public TestEntity3()
	{
		super();
		
		//FIXME ORDER IS IMPORTANT
		add(createStatsComponent(),
			createGraphicsComponent(),
			createMovementComponent(), 
			createRigidBodyComponent(),
			createAbilityComponent(),
			createAIComponent(),
			new ModifierComponent(),
			createDropComponent(),
			new SpawnComponent(),
			createLifetimeComponent());
	}
	
	private EntityComponent createStatsComponent()
	{
		StatsComponent comp = 
				new StatsComponent();
	
		comp.getStats()
			.setCoreStatValue(CoreStatType.SPEED, 5);
		
		return comp;
	}
	
	
	private EntityComponent createGraphicsComponent()
	{
		GraphicsComponent comp = new GraphicsComponent();
		
		LayeredGraphic graphic = new LayeredGraphic();
	
		graphic.addGraphic(new ShapeGraphic(), 
						   new Vector2D.Double(-5, -5));
		
		comp.setGraphic(graphic);
		
		return comp;
	}
	
	private EntityComponent createRigidBodyComponent()
	{
		RigidBodyComponent comp = new RigidBodyComponent();
		
		comp.setBodyType(BodyType.STRUCTURE);
		
		comp.getRigidBody()
			.addComponent(
					new Vector2D.Double(-5, -5),
					new Rectangle2D.Double(0, 0, 10, 10));
		
		//comp.addCollisionResponses(BodyType.STRUCTURE, new BodyPusher());
		
		return comp;
	}
	
	private EntityComponent createAbilityComponent()
	{
		TestEntity2 abilityEntity = new TestEntity2();
		
		ActiveAbility ability = new ActiveAbility();
		
		ability.addAbilityEntity(abilityEntity, new ProjectileInitiator());
		
		AbilityComponent comp = new AbilityComponent();
		
		comp.addActiveAbility(ability);
		
		return comp;
	}
	
	private EntityComponent createMovementComponent()
	{
		MovementComponent comp =
				new MovementComponent();
		
		comp.setNormalMovement(new TargetedMovement());
		
		return comp;
	}
	
	private EntityComponent createLifetimeComponent()
	{
		LifetimeComponent comp = 
				new LifetimeComponent();
		
		return comp;
	}
	
	private EntityComponent createAIComponent()
	{
		AIComponent comp =
				new AIComponent();
		
		comp.addBehaviours(new AssaultBehaviour(),
						  new AttackBehaviour());
		
		return comp;
	}
	
	private EntityComponent createDropComponent()
	{
		DropComponent comp =
				new DropComponent();
		
		comp.addDropItem(new InstantItem(), 1.00);
		
		return comp;
	}
}
