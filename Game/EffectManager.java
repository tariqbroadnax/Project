package Game;

import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

import Modifiers.Effect;

public class EffectManager 
	implements Updatable
{
	private Collection<Effect> effects;
	
	public void EffectManager()
	{
		effects = new LinkedList<Effect>();
	}

	@Override
	public void update(Duration delta) 
	{
		effects.forEach(e -> e.update(delta));
		effects.removeIf(e -> e.isFinished());
	}

	public void addEffect(Effect effect) {
		effects.add(effect);
	}
}
