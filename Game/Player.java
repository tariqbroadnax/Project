package Game;

import Behaviour.BasicAttackBehaviour;
import Behaviour.PathingBehaviour;
import EntityComponent.AbilityComponent;
import EntityComponent.BehaviourComponent;
import EntityComponent.BeingGraphicsComp;
import EntityComponent.EntityComponent;
import EntityComponent.LifetimeComponent;
import EntityComponent.LightComponent;
import EntityComponent.StatsComponent;
import Movement.MovementComponent;

public class Player extends Entity
{	
	private PathingBehaviour pathingBeh;
	private BasicAttackBehaviour attackBeh;
	
	public Player()
	{
		super();
		
		add(new LightComponent(),
			new MovementComponent(),
			new LifetimeComponent(),
			new StatsComponent(),
			new AbilityComponent(),
			createBehaviourComponent(),
			new BeingGraphicsComp());
	}

	private EntityComponent createStatsComponent()
	{
		StatsComponent comp = 
				new StatsComponent();
		
		return comp;
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
