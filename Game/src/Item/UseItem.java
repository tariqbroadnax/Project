package Item;

import java.util.ArrayList;
import java.util.List;

import Entity.Entity;
import EntityComponent.EffectComponent;
import Modifiers.Effect;

public class UseItem extends Item
{
	private List<Effect> effects;
	
	public UseItem()
	{
		effects = new ArrayList<Effect>();
	}

	public void use(Entity ent)
	{	
		if(quantity > 0)
		{
			for(Effect effect : effects)
				ent.get(EffectComponent.class)
				   .apply(effect);
		
			quantity--;
		}
	}
	
	public void addEffect(Effect effect) {
		effects.add(effect);
	}
	
	public void removeEffect(Effect effect) {
		effects.remove(effect);
	}
}
