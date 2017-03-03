package EntityComponent;

import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

import Modifiers.Effect;

public class EffectComponent extends EntityComponent
{
	private Collection<Effect> effects;
	
	public EffectComponent()
	{
		effects = new LinkedList<Effect>();
	}
	
	@Override
	public void update(Duration delta) 
	{
		for(Effect effect : effects)
		{
			effect.update(delta);
			
			if(effect.isFinished())
			{
				effect.stop();
				effect.setTarget(null);
			}
		}
			
		
		effects.removeIf(eff -> eff.isFinished());
	}
	
	public void add(Effect effect) 
	{
		if(effect.canBeApplied(parent))
		{
			effects.add(effect);
			effect.setTarget(parent);
			effect.start();
		}
	}

	public void remove(Effect effect) 
	{
		effects.remove(effect);
		effect.stop();
		effect.setTarget(null);
	}
	
	public void remove(Class<? extends Effect> c)
	{
		Effect target = null;
		
		for(Effect effect : effects)
			if(effect.getClass().equals(c))
				target = effect;
		
		if(target != null)
			remove(target);
	}
	
	public boolean contains(Effect effect) 
	{
		return effects.contains(effect);
	}
	
	@Override
	protected EntityComponent _clone() {
		return new EffectComponent();
	}
}
