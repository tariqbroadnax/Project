package Modifiers;

import EntityComponent.AbilityComponent;

public class InvincibleCasting extends InstantEffect
{
	@Override
	public void apply() 
	{
		target.get(AbilityComponent.class)
			  .setCastInterruptedOnAttack(false);
	}
	
	public void unapply()
	{
		target.get(AbilityComponent.class)
		  .setCastInterruptedOnAttack(true);
	}

	@Override
	public Object clone() {
		return new InvincibleCasting();
	}
}
