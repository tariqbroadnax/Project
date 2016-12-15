package Modifiers;

import Entity.Entity;
import EntityComponent.StatsComponent;
import Stat.Stats;

public class Heal
{
	private double val;
	
	public Heal()
	{
		val = 10;
	}
	
	public void apply(Entity target)
	{
		Stats stats = target.get(StatsComponent.class)
							.getStats();
		
		stats.addHealth(val);
	}
}
