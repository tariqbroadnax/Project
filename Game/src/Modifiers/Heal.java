package Modifiers;

import EntityComponent.CombatComponent;

public class Heal extends InstantEffect
{
	private double flat, scale, perc;
	
	public Heal()
	{
		flat = 10;
		scale = 0;
		perc = 0;
	}
	
	public Heal(Heal heal)
	{
		super(heal);
		
		flat = heal.flat;
		scale = heal.scale;
		perc = heal.perc;
	}

	@Override
	public void apply() 
	{
		src.get(CombatComponent.class)
		  .applyHeal(this);
	}

	public double getFlatAmount() {
		return flat;
	}
	
	public double getScaledAmount() {
		return scale;
	}
	
	public double getPercentAmount() {
		return perc;
	}

	@Override
	public Object clone() {
		return new Heal(this);
	}
}