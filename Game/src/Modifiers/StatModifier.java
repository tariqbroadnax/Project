package Modifiers;

import Entity.Entity;
import EntityComponent.StatsComponent;

public class StatModifier extends InstantEffect
{
	private Stat stat;
	
	private Modifier mod;
	
	public StatModifier()
	{
		stat = Stat.SPEED;
		
		mod = new AddModifier(50);
	}
	
	public StatModifier(StatModifier effect)
	{
		super(effect);
		
		stat = effect.stat;
		
		mod = (Modifier) effect.mod.clone();
	}

	@Override
	public void apply() 
	{
		src.get(StatsComponent.class)
		   .getStats()
		   .addStatModifier(this);
	}

	@Override
	public void unapply() 
	{
		src.get(StatsComponent.class)
		   .getStats()
		   .removeStatModifier(this);
	}

	@Override
	public boolean canBeApplied(Entity target) {
		return target.contains(StatsComponent.class);
	}
	
	public void setStat(Stat stat) {
		this.stat = stat;
	}
	
	public void setModifier(Modifier mod) {
		this.mod = mod;
	}
	
	public Stat getStat() {
		return stat;
	}
	
	public Modifier getModifier() {
		return mod;
	}
	
	@Override
	public Object clone() {
		return new StatModifier(this);
	}
}
