package Entity;

import java.awt.geom.Rectangle2D;

import Behavior.BehaviorComponent;
import EntityComponent.AbilityComponent;
import EntityComponent.CombatComponent;
import EntityComponent.DropComponent;
import EntityComponent.EffectComponent;
import EntityComponent.GraphicsComponent;
import EntityComponent.LifetimeComponent;
import EntityComponent.RigidBodyComponent;
import EntityComponent.StatsComponent;
import Movement.MovementComponent;

public class Monster extends Entity
{
	public Monster()
	{
		add(new StatsComponent(),
			new CombatComponent(),
			new RigidBodyComponent(),
			new LifetimeComponent(),
			new DropComponent(),
			new AbilityComponent(),
			new EffectComponent(),
			new BehaviorComponent(),
			new MovementComponent(),
			new GraphicsComponent());
		
		get(RigidBodyComponent.class)
		.getRigidBody()
		.addLimbs(new Rectangle2D.Double(-5, -5, 10, 10));

//		get(BehaviorComponent.class)
//		.addBehavior(new MonsterBehavior());
//		
//			new EventGraphicsComponent());
	}
}
