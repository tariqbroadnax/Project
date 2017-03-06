package Ability;

import EntityComponent.EffectComponent;
import Modifiers.Damage;

public class BasicAttack extends TargetUnitAbility
{
	private Damage damage;
	
	public BasicAttack()
	{
		damage = new Damage();
		
		damage.setScaleAmount(0);
		damage.setFlatAmount(1);
		
		setRange(15);
	}
	
	@Override
	public void start()
	{
		damage.setSource(src);
	}
	
	public void cast()
	{
		super.cast();	
	
		target.get(EffectComponent.class)
			  .apply(damage);
	}
}
