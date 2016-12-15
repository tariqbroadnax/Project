package Modifiers;

import java.time.Duration;

import Entity.Entity;
import EntityComponent.Lifetime;
import EntityComponent.StatsComponent;
import Movement.MovementComponent;
import Stat.Stats;

public class ModifierEffect extends Effect
{
	private Stat stat;
	private AddModifier addMod;
	private MultModifier multMod;
	
	private Lifetime lifetime;
	
	public ModifierEffect()
	{
		stat = Stat.SPEED;
		addMod = new AddModifier();
		multMod = new MultModifier();
		lifetime = new Lifetime(3000);
	}
	
	public ModifierEffect(
			Stat stat, 
			double addFactor, double multFactor,
			long lifetime)
	{
		super();
		
		this.stat = stat;
		
		addMod.setFactor(addFactor);
		multMod.setFactor(multFactor);
	}
	
	public ModifierEffect(ModifierEffect effect)
	{
		stat = effect.stat;
		
		addMod = new AddModifier(effect.addMod);
		multMod = new MultModifier(effect.multMod);
		lifetime = new Lifetime(effect.lifetime);
	}

	@Override
	public void update(Duration delta) {
		lifetime.update(delta);
	}

	@Override
	public void start() 
	{
		switch(stat)
		{
		case SPEED:
			MovementComponent moveComp =
				target.get(MovementComponent.class);
			
			moveComp.addSpeedMod(addMod);
			moveComp.addSpeedMod(multMod);
			break;
			
		case HEALTH:
			Stats stats =
				target.get(StatsComponent.class)
					  .getStats();
			
			stats.addHealthMod(addMod);
			stats.addHealthMod(multMod);
			break;
		}
	}

	@Override
	public void stop() 
	{
		switch(stat)
		{
		case SPEED:
			MovementComponent moveComp =
				target.get(MovementComponent.class);
			
			moveComp.removeSpeedMod(addMod);
			moveComp.removeSpeedMod(multMod);
			break;
			
		case HEALTH:
			Stats stats =
				target.get(StatsComponent.class)
					  .getStats();
			
			stats.removeHealthMod(addMod);
			stats.removeHealthMod(multMod);
			break;
		}
	}
	
	public void setAddFactor(double factor) {
		addMod.setFactor(factor);
	}
	
	public void setMultFactor(double factor) {
		multMod.setFactor(factor);
	}
	
	public void setStat(Stat stat) {
		this.stat = stat;
	}
	
	public void setLifetime(long lifetime) {
		this.lifetime.setRemaining(lifetime);
	}

	@Override
	public boolean isFinished() {
		return lifetime.isLifeOver();
	}

	@Override
	protected Effect _clone() {
		return new ModifierEffect(this);
	}

	// FIXME
	@Override
	public boolean canBeApplied(Entity target) {
		// TODO Auto-generated method stub
		return false;
	}
}
