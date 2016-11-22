package EntityComponent;

import java.time.Duration;

import Game.Entity;
import Movement.Movement;
import Movement.MovementComponent;
import Stat.Stats;

public class StatsComponent extends EntityComponent
{
	private Stats stats;
	
	private Movement movement;
	private Lifetime lifetime;
	
	public StatsComponent()
	{
		stats = new Stats();		
	}
	
	public StatsComponent(StatsComponent comp)
	{
		this();		
	}

	@Override
	public void update(Duration delta)
	{
		double speed = stats.getSpeed();
		
		movement.setSpeed(speed);
		
		
		double health = stats.getHealth();
		
		if(health < 0)
			lifetime.end();
	}
	
	public void setParent(Entity parent)
	{
		super.setParent(parent);
		
		if(parent == null) 
			return;
		
		movement = parent.get(MovementComponent.class)
						 .getNormalMovement();
	
		lifetime = parent.get(LifetimeComponent.class)
						 .getLifetime();
	}
	
	public Stats getStats() {
		return stats;
	}
	
	@Override
	protected EntityComponent _clone() {
		return new StatsComponent(this);
	}
	
	public String toString()
	{
		return super.toString() + '\n' +
			   stats.toString();
	}
}
