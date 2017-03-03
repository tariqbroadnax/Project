package Ability;

public class TeleportAbility extends PointAbility
{
	public TeleportAbility()
	{
		setCooldown(600);
		setCastTime(500);
	}
	
	public void activate()
	{
		super.activate();
		
		src.setLoc(target);
	}
}
