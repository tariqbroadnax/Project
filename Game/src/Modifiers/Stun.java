package Modifiers;

import EntityComponent.AbilityComponent;

public class Stun extends Root
{
	protected void apply()
	{
		super.apply();
		
		target.get(AbilityComponent.class)
			  .setAllActivesEnabled(false);
	}
	
	public void revert()
	{
		super.revert();
		
		target.get(AbilityComponent.class)
		  	  .setAllActivesEnabled(true);
	}
}
