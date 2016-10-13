package TestEntity;

import java.awt.geom.Rectangle2D;

import Ability.ActiveAbility;
import AbilityInitiators.ProjectileInitiator;
import EntityComponent.AbilityComponent;
import EntityComponent.EntityComponent;
import EntityComponent.GraphicsComponent;
import EntityComponent.InventoryComponent;
import EntityComponent.LifetimeComponent;
import EntityComponent.MetaComponent;
import EntityComponent.ModifierComponent;
import EntityComponent.RigidBodyComponent;
import EntityComponent.SpawnComponent;
import EntityComponent.StatsComponent;
import Game.Entity;
import Graphic.LayeredGraphic;
import Graphic.ShapeGraphic;
import Graphic.Vector2D;
import Movement.MovementComponent;
import Stat.CoreStatType;

public class TestEntity extends Entity
{
	public TestEntity()
	{
		super();
		
		add(createStatsComponent(),
			createGraphicsComponent(),
			createMovementComponent(), 
			createRigidBodyComponent(),
			createAbilityComponent(),
			createLifetimeComponent(),
			new InventoryComponent(),
			new ModifierComponent(),
			new SpawnComponent());
		
		get(MetaComponent.class).setPlayer(true);
	}
	
	private EntityComponent createMovementComponent()
	{
		MovementComponent comp = 
				new MovementComponent();
		
		comp.getNormalMovement()
			.setEnabled(false);
		
		return comp;
	}
	
	private EntityComponent createStatsComponent()
	{
		StatsComponent comp = 
				new StatsComponent();
		
		comp.getStats()
			.setCoreStatValue(CoreStatType.SPEED, 80);
		
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
	
	private EntityComponent createLifetimeComponent()
	{
		LifetimeComponent comp = 
				new LifetimeComponent();
		
		return comp;
	}
}
