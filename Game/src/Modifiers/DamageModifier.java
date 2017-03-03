package Modifiers;

import java.util.ArrayList;
import java.util.List;

import Ability.Ability;
import Ability.ActiveAbility;
import EntityComponent.CombatComponent;

public class DamageModifier extends InstantEffect
{
	private Modifier mod;
	
	private List<Class<? extends ActiveAbility>> targets;
	
	public DamageModifier()
	{
		mod = new AddModifier(50);

		targets = new ArrayList<Class<? extends ActiveAbility>>();
	}
	
	public DamageModifier(DamageModifier effect)
	{
		mod = (Modifier) mod.clone();
		
		targets = new ArrayList<Class<? extends ActiveAbility>>();
		
		for(Class<? extends ActiveAbility> c : effect.targets)
			targets.add(c);
	}
	
	public void apply()
	{
		CombatComponent comp = src.get(CombatComponent.class);
		
		comp.addDamageModifier(this);
	}
	
	public void unapply()
	{
		CombatComponent comp = src.get(CombatComponent.class);
		
		comp.removeDamageModifier(this);
	}
	
	public boolean canModifyDamage(Ability ability)
	{
		if(targets.size() == 0)
			return true;
		else
			return targets.contains(ability.getClass());
	}
	
	public void setModifier(Modifier mod) {
		this.mod = mod;
	}
	
	public Modifier getModifier() {
		return mod;
	}
	
	public void addAbilityTarget(Class<? extends ActiveAbility> target) {
		targets.add(target);
	}
	
	public void removeAbilityTarget(Class<? extends ActiveAbility> target) {
		targets.remove(target);
	}
	
	public Object clone() {
		return new DamageModifier(this);
	}
}
