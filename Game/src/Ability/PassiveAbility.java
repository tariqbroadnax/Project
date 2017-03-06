package Ability;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import EntityComponent.EffectComponent;
import Modifiers.Effect;

public class PassiveAbility extends Ability
{			
	private List<Effect> effects;
	
	public PassiveAbility()
	{
		effects = new ArrayList<Effect>();
	}
	
	public void start() 
	{
		super.start();
		
		EffectComponent effComp = src.get(EffectComponent.class);
		
		for(Effect eff : effects)
			effComp.apply(eff);
	}
	
	public void stop() 
	{
		EffectComponent effComp = src.get(EffectComponent.class);
		
		for(Effect eff : effects)
			effComp.unapply(eff);
	}

	@Override
	public void update(Duration delta) {
		
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return null;
	}
}
