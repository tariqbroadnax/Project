package EntityComponent;

import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

import Modifiers.Effect;
import Modifiers.GradualEffect;
import Modifiers.InstantEffect;

public class EffectComponent extends EntityComponent
{
	private Collection<GradualEffect> effects;
	
	public EffectComponent()
	{
		effects = new LinkedList<GradualEffect>();
	}
	
	@Override
	public void update(Duration delta) 
	{
		for(GradualEffect effect : effects)
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
	
	public void apply(Effect effect)
	{
		if(effect instanceof InstantEffect)
		{
			InstantEffect instEff = (InstantEffect) effect;
			
			apply(instEff);
		}
		else
		{
			GradualEffect gradEff = (GradualEffect) effect;
			
			apply(gradEff);
		}
	}
	public void apply(InstantEffect effect) 
	{
		if(effect.canBeApplied(parent))
		{
			effect.setTarget(parent);
			effect.apply();
		}
	}
	
	public void apply(GradualEffect effect)
	{
		if(effect.canBeApplied(parent))
		{
			effects.add(effect);
			effect.setTarget(parent);
			effect.start();
		}
	}

	public void unapply(Effect eff) 
	{
		if(effects.contains(eff))
		{
			effects.remove(eff);
			eff.setTarget(null);
			
			((GradualEffect) eff).stop();
		}
	}
	
//	public void remove(GradualEffect effect) 
//	{
//		effects.remove(effect);
//		effect.setTarget(null);
//	}
//	
	
	public boolean contains(Effect effect) 
	{
		return effects.contains(effect);
	}
	
	@Override
	protected EntityComponent _clone() {
		return new EffectComponent();
	}

}
