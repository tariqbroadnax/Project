package Ability;

import Entity.Entity;
import EntityComponent.EffectComponent;
import Modifiers.Effect;
import Modifiers.ModifierEffect;

public class TargetSourceAbility 
	extends TargetUnitAbility
{	
	public TargetSourceAbility()
	{
		super();
		
		ModifierEffect effect = 
				new ModifierEffect();
		
		effect.setMultFactor(4);
		
		effects.add(effect);
	}
	
	public void activate()
	{
		super.activate();
		
		EffectComponent comp = 
				src.get(EffectComponent.class);
		
		for(Effect effect : effects)
			comp.add((Effect)effect.clone());
	}
	
	public void setSrc(Entity src)
	{
		super.setSrc(src);
		super.setTarget(src);
		
		for(Effect effect : effects)
			effect.setSource(src);
	}
	
	@Deprecated
	public void setTarget(Entity target) { return; }
}
