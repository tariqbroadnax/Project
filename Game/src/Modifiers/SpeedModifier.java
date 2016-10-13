package Modifiers;

import EntityComponent.StatsComponent;
import Stat.CoreStatType;

public class SpeedModifier extends Modifier
{
	private double add, mult;

	private StatModifier mod;
	
	public SpeedModifier()
	{
		super();
		
		add = 0;
		mult = 1;
	}
	
	public SpeedModifier(double add, double mult)
	{
		super();
		
		this.add = add;
		this.mult = mult;
	}
	
	public SpeedModifier(SpeedModifier mod)
	{
		super();
		
		add = mod.add;
		mult = mod.mult;
	}
	
	@Override
	protected void apply()
	{		
		mod = speed -> add + mult * speed;
	
		target.get(StatsComponent.class)
			  .getStats()
			  .addCoreStatModifier(CoreStatType.SPEED, mod);
	}
	
	public void revert()
	{
		target.get(StatsComponent.class)
		  .getStats()
		  .removeCoreStatModifier(
				  CoreStatType.SPEED, mod);
	}
	
	public void setAddAmount(double add)
	{
		this.add = validAddAmount(add) ? add : this.add;
	}
	
	public void setMultAmount(double mult)
	{
		this.mult = validMultAmount(mult) ? mult : this.mult;
	}
	
	protected boolean validAddAmount(double add)
	{
		return true;
	}
	
	protected boolean validMultAmount(double mult)
	{
		return mult >= 0;
	}

	@Override
	protected Object _clone()
	{
		return new SpeedModifier(this);
	}
}
