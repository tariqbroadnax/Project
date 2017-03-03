package Quest;

import Entity.Entity;
import EntityComponent.StatsComponent;

public class GoldReward implements Reward
{
	private int gold;
	
	public GoldReward()
	{
		gold = 5;
	}
	
	public GoldReward(int gold)
	{
		this.gold = gold;
	}
	
	public GoldReward(GoldReward reward)
	{
		gold = reward.gold;
	}
	
	@Override
	public void give(Entity ent) 
	{
		ent.get(StatsComponent.class)
		   .getStats()
		   .addGold(gold);
	}

	public Object clone() {
		return new GoldReward(this);
	}
}
