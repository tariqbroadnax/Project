package Modifiers;

public class Haste extends SpeedModifier
{
	public Haste()
	{
		super(15, 0);
	}
	
	protected boolean validAddAmount(double add)
	{
		return add >= 0;
	}
	
	protected boolean validMultAmount(double mult)
	{
		return mult >= 1;
	}
}
