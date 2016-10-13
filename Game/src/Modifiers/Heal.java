package Modifiers;

public class Heal extends HealthModifier
{
	public Heal()
	{
		super(10);
	}
	
	protected boolean validAmount(double amount)
	{
		return amount >= 0;
	}
}
