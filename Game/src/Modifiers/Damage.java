package Modifiers;

import EntityComponent.StatsComponent;
import Stat.CoreStatType;
import Stat.Stats;

public class Damage extends Modifier
{
	private int flatAmount;
	
	private int ratio; // 330 -> 
	
	private boolean physical;
	
	public Damage(int flatAmount, int ratio)
	{
		this.ratio = ratio;
	}
	
	@Override
	protected void apply()
	{
		Stats srcStats =
				src.get(StatsComponent.class)	
				   .getStats(),
			 targetStats =
			 	target.get(StatsComponent.class)
			 	 	  .getStats();
			
		int amount = (int) (
				srcStats.getValue(CoreStatType.PHYS_ATK)
				 * ratio / 100.0);
		
		targetStats.recieveDamage(amount, src);
	}
	
	@Override
	protected Object _clone() 
	{
		return null;
	}	
	
}
