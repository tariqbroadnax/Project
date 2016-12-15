package Modifiers;

import EntityComponent.AbilityComponent;

public class Stun extends Root
{
	public void start() 
	{
		super.start();
		
		target.get(AbilityComponent.class)
			  .setEnabled(false);
	}
	
	public void stop()
	{
		target.get(AbilityComponent.class)
		  	  .setEnabled(true);
	}
}
