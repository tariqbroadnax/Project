package Entity;

import java.time.Duration;

import Behaviour.BasicAttackBehaviour;
import Behaviour.PathingBehaviour;
import EntityComponent.AbilityComponent;
import EntityComponent.BehaviourComponent;
import EntityComponent.BeingGraphicsComp;
import EntityComponent.EffectComponent;
import EntityComponent.LifetimeComponent;
import EntityComponent.RigidBodyComponent;
import EntityComponent.StatsComponent;
import Movement.MovementComponent;

public class Player extends Entity
{	
	private PathingBehaviour pathingBeh;
	private BasicAttackBehaviour attackBeh;
	
	public Player()
	{
		super();
		
		add(new MovementComponent(),
			new BeingGraphicsComp(),
			new RigidBodyComponent(),
			new LifetimeComponent(),
			new StatsComponent(),
			new AbilityComponent(),
			createBehaviourComponent(),
			new EffectComponent());
	}
	
	private BehaviourComponent createBehaviourComponent()
	{
		pathingBeh = new PathingBehaviour();
		attackBeh = new BasicAttackBehaviour();
		
		BehaviourComponent comp =
				new BehaviourComponent();
		
		comp.addBehaviour(pathingBeh);
		comp.addBehaviour(attackBeh);
		
		return comp;
	}
	
	public PathingBehaviour getPathingBehaviour() {
		return pathingBeh;
	}
	
	public BasicAttackBehaviour getAttackBehaviour() {
		return attackBeh;
	}
}
