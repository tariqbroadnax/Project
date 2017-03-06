package EntityComponent;

import java.time.Duration;

import Entity.Entity;
import Movement.MovementComponent;
import Stat.Stats;

public class StatsComponent extends EntityComponent
{
	private Stats stats;
	
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
		stats.update(delta);
		
		double health = stats.getHealth();
		
		if(health == 0 && parent.contains(LifetimeComponent.class))
		{
			parent.get(LifetimeComponent.class)
			  	  .getLifetime()
				  .end();			  
		}
		
		double speed = stats.getSpeed();
		
		if(parent.contains(MovementComponent.class))
		{
			parent.get(MovementComponent.class)
				  .getMovement()
				  .setDefaultSpeed(speed);
		}
		
		if(parent.contains(AbilityComponent.class))
		{
			long atkSpeedCastTime = (long) (1000.0 / stats.getAttackSpeed());
					
			parent.get(AbilityComponent.class)
				  .getBasicAttack()
				  .setCooldown(atkSpeedCastTime);
			
			parent.get(AbilityComponent.class)
			  .getBasicAttack()
			  .setCastTime(atkSpeedCastTime);
		}
	}
	
	public void setParent(Entity parent)
	{
		super.setParent(parent);
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
