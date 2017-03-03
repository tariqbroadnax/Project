package Modifiers;

import EntityComponent.StatsComponent;

public class GoldOverTime extends TickEffect
{
	private int amount;
	
	public GoldOverTime()
	{
		amount = 5;
	}
	
	public GoldOverTime(GoldOverTime effect)
	{
		super(effect);
		
		amount = effect.amount;
	}
	
	@Override
	protected void apply() 
	{
		target.get(StatsComponent.class)
			  .getStats()
			  .addGold(amount);
	}
	
	public void setAmount(int amount) {
		this.amount = amount;
	}
	
	public int getAmount() {
		return amount;
	}

	@Override
	protected Effect _clone() {
		return new GoldOverTime(this);
	}
}
