package Modifiers;

import java.time.Duration;

import Entity.Entity;
import EntityComponent.CombatComponent;
import EntityComponent.CombatListener;
import EntityComponent.StatsComponent;

public class HealOnKill extends Effect
	implements CombatListener
{
	private double amount;

	public HealOnKill()
	{
		amount = 5;
	}
	
	public HealOnKill(double amount)
	{
		this.amount = amount;
	}
	
	public HealOnKill(HealOnKill effect)
	{
		amount = effect.amount;
	}
	
	@Override
	public void update(Duration delta) {}

	@Override
	public void start() 
	{
		src.get(CombatComponent.class)
		   .addCombatListener(this);
	}

	@Override
	public void stop() 
	{
		src.get(CombatComponent.class)
		   .removeCombatListener(this);
	}

	@Override
	public boolean canBeApplied(Entity target) {
		return true;
	}

	@Override
	public boolean isFinished() {
		return false;
	}

	@Override
	protected Effect _clone() {
		return new HealOnKill(this);
	}

	@Override
	public void entityKilled(Entity ent) 
	{
		ent.get(StatsComponent.class)
		   .getStats()
		   .heal(amount);
	}
}
