package Ability;

import java.util.ArrayList;
import java.util.List;

import EntityComponent.EffectComponent;
import Modifiers.Effect;
import Modifiers.Heal;

public class InstantFriendlyUnitAbility 
	extends FriendlyUnitAbility
{
	private List<Effect> effects;
	
	public InstantFriendlyUnitAbility()
	{
		effects = new ArrayList<Effect>();
		
		effects.add(new Heal());
		
		setCooldown(500);
		setCastTime(500);
	}
	
	public InstantFriendlyUnitAbility(
			InstantFriendlyUnitAbility ability)
	{
		this();
		
		for(Effect eff : ability.effects)
		{
			eff = (Effect) eff.clone();
			
			effects.add(eff);
		}
	}
	
	public void activate()
	{
		super.activate();
		
		EffectComponent comp = target.get(EffectComponent.class);
	
		for(Effect eff : effects)
		{
			eff = (Effect) eff.clone();
			
			eff.setSource(src);
			
			comp.apply(eff);
		}
	}	
}
