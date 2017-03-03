package Modifiers;

import EntityComponent.CombatComponent;

public class GuardDisable extends InstantEffect
{
	private boolean blockDisabled,
					dodgeDisabled;

	public GuardDisable()
	{
		blockDisabled = dodgeDisabled = true;
	}
	
	public GuardDisable(GuardDisable effect)
	{
		blockDisabled = effect.blockDisabled;
		dodgeDisabled = effect.dodgeDisabled;
	}
	
	@Override
	public void apply() 
	{
		CombatComponent comp = target.get(CombatComponent.class);
		
		if(blockDisabled)
			comp.setBlockEnabled(false);
		if(dodgeDisabled)
			comp.setDodgeEnabled(false);
	}
	
	public void unapply()
	{
		CombatComponent comp = target.get(CombatComponent.class);
		
		if(blockDisabled)
			comp.setBlockEnabled(true);
		if(dodgeDisabled)
			comp.setDodgeEnabled(true);
	}

	@Override
	public Object clone() {
		return new GuardDisable(this);
	}
					
}
