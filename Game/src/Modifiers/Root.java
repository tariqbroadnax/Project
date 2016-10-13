package Modifiers;

public class Root extends SpeedModifier
{
	public Root()
	{
		super(0, 0);
	}
	
	protected boolean validAddAmount(double add)
	{
		return false;
	}
	
	protected boolean validMultAmount(double mult)
	{
		return false;
	}
}
