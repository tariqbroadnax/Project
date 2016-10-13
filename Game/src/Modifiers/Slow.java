package Modifiers;

public class Slow extends SpeedModifier
{
	public Slow()
	{
		super(0, 1);
	}

	protected boolean validAddAmount(double add)
	{
		return add <= 0;
	}
	
	protected boolean validMultAmount(double mult)
	{
		return 0 < mult && mult <= 1;
	}
}
