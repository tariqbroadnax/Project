package CollisionResponses;

import java.util.Collection;

import EntityComponent.CollisionEvent;
import EntityComponent.CollisionResponse;
import EntityComponent.EffectComponent;
import Modifiers.Effect;

public class ApplyEffects implements CollisionResponse
{
	private Collection<Effect> effects;
	
	public ApplyEffects(Collection<Effect> effects)
	{
		this.effects = effects;
	}
	
	@Override
	public void collisionOccurred(CollisionEvent e) 
	{
		EffectComponent comp = 
				e.collided.get(EffectComponent.class);
		
		for(Effect effect : effects)
			comp.add((Effect)effect.clone());
	}
	
	public Object clone()
	{
		return new ApplyEffects(this.effects);
	}
}
