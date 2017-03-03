package Modifiers;

import EntityComponent.EffectComponent;

public class RemoveEffect extends InstantEffect
{
	private Class<? extends Effect> target;
	
	public RemoveEffect(){}
	
	public RemoveEffect(RemoveEffect effect)
	{
		target = effect.target;
	}
	
	@Override
	public void apply() 
	{
		EffectComponent comp = super.target.get(EffectComponent.class);
		
		comp.remove(target);
	}

	@Override
	public Object clone() {
		return new RemoveEffect(this);
	}
}
