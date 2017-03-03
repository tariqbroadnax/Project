package Quest;

import Entity.Entity;
import EntityComponent.StatsComponent;

public class LevelRequirement implements Requirement
{
	private int minLvl, maxLvl;
	
	public LevelRequirement()
	{
		this(0);
	}
	
	public LevelRequirement(int minLvl)
	{
		this(minLvl, Integer.MAX_VALUE);
	}
	
	public LevelRequirement(int minLvl, int maxLvl)
	{
		this.minLvl = minLvl;
		this.maxLvl = maxLvl;
	}
	
	public LevelRequirement(LevelRequirement req)
	{
		minLvl = req.minLvl;
		maxLvl = req.maxLvl;
	}
	
	@Override
	public boolean isSatisfied(Entity ent) 
	{
		int entLvl = ent.get(StatsComponent.class)
						.getStats()
						.getLevel();
		
		return minLvl <= entLvl && entLvl <= maxLvl;
	}
	
	public void setMinLevel(int minLvl) {
		this.minLvl = minLvl;
	}
	
	public void setMaxLevel(int maxLvl) {
		this.maxLvl = maxLvl;
	}
	
	public int getMinLevel() {
		return minLvl;
	}
	
	public int getMaxLevel() {
		return maxLvl;
	}
	
	public Object clone() {
		return new LevelRequirement(this);
	}
}
