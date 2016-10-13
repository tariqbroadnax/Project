package EntityComponent;

import java.time.Duration;

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
		
		stats = new Stats(comp.stats);		
	}
	
	public Stats getStats()
	{
		return stats;
	}

	@Override
	public void update(Duration delta){}
	
	@Override
	protected EntityComponent _clone()
	{
		return new StatsComponent(this);
	}
	
	public String toString()
	{
		return super.toString() + '\n' +
			   stats.toString();
	}
}
