package Item;

import java.util.ArrayList;
import java.util.List;

import Entity.Entity;
import EntityComponent.EffectComponent;
import Modifiers.Effect;

public class EquipItem extends Item 
{
	private int requiredLvl;
	
	private List<Effect> effects;
	
	public EquipItem()
	{
		requiredLvl = 1;
		
		effects = new ArrayList<Effect>();
	}
	
	public void equip(Entity ent)
	{
		if(ent.contains(EffectComponent.class))
		{
			EffectComponent comp = ent.get(EffectComponent.class);
		
			for(Effect effect : effects)
				comp.add(effect);
		}
		else
		{
			System.out.println("Item.EquipItem: Cannot add Effects from " + this);
		}
	}
	
	public void unequip(Entity ent)
	{
		if(ent.contains(EffectComponent.class))
		{
			EffectComponent comp = ent.get(EffectComponent.class);
			
			for(Effect effect : effects)
				comp.remove(effect);
		}
	}
	
	public void setRequiredLvl(int requiredLvl) {
		this.requiredLvl = requiredLvl;
	}
	
	public void addEffect(Effect effect) {
		effects.add(effect);
	}
	
	public void removeEffect(Effect effect) {
		effects.remove(effect);
	}
	
	public int getRequiredLvl() {
		return requiredLvl;
	}
 }
