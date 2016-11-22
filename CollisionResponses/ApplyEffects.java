package CollisionResponses;

import java.util.Collection;

import EntityComponent.CollisionEvent;
import EntityComponent.CollisionResponse;
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
		for(Effect effect : effects)
			effect.apply(e.collided);
	}
}
