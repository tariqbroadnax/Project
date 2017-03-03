package Quest;

import Entity.Entity;
import EntityComponent.StatsComponent;
import Stat.Stats;

public class ExpReward implements Reward
{
	private int exp;
	
	public ExpReward()
	{
		exp = 5;
	}
	
	public ExpReward(int exp)
	{
		this.exp = exp;
	}
	
	public ExpReward(ExpReward reward)
	{
		exp = reward.exp;
	}
	
	@Override
	public void give(Entity ent) 
	{
		Stats stats = ent.get(StatsComponent.class)
						 .getStats();
		
		stats.addExp(exp);
	}
	
	public Object clone() {
		return new ExpReward(this);
	}
}
