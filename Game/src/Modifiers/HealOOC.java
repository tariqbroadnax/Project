package Modifiers;

import java.time.Duration;

import Entity.Entity;
import EntityComponent.CombatComponent;
import EntityComponent.StatsComponent;
import Stat.Stats;

public class HealOOC extends Effect
{
	private double amount;
	
	private long tickDelay, elapsed;
	
	public HealOOC()
	{
		amount = 5;
		
		tickDelay = elapsed = 0;
	}
	
	public HealOOC(double amount, long tickDelay)
	{
		this.amount = amount;
		this.tickDelay = tickDelay;
	}
	
	public HealOOC(HealOOC effect)
	{
		amount = effect.amount;
		tickDelay = effect.tickDelay;
	}
	
	@Override
	public void update(Duration delta) 
	{
		elapsed += delta.toMillis();
		
		boolean OOC = src.get(CombatComponent.class)
						 .isOutOfCombat();
		
		if(OOC && elapsed > tickDelay)
		{
			Stats stats = target.get(StatsComponent.class)
								.getStats();
			
			stats.heal(amount);
			
			elapsed = 0;
		}
		else
			elapsed = 0;
	}

	@Override
	public void start() {}

	@Override
	public void stop() {}

	@Override
	public boolean canBeApplied(Entity target) {
		return true;
	}

	@Override
	public boolean isFinished() {
		return true;
	}

	@Override
	protected Effect _clone() {
		return new HealOOC(this);
	}
}
