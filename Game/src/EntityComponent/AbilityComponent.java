package EntityComponent;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;

import Ability.AbilityListener;
import Ability.ActiveAbility;
import Ability.CastingIndicator;
import Ability.FreeProjectileAbility;
import Ability.HomingProjectileAbility;
import Ability.TargetSourceAbility;
import Entity.Entity;
import Modifiers.Root;

public class AbilityComponent extends EntityComponent
{
	public static final int BASIC_ATTACK_INDEX = 0;
	
	private ArrayList<ActiveAbility> actives;
	private ArrayList<AbilityListener> lists;
		
	private boolean enabled, casting;
	
	private Root castRoot;
	
	private CastingIndicator indicator;

	public AbilityComponent()
	{
		super();
		
		actives = new ArrayList<ActiveAbility>();
		lists = new ArrayList<AbilityListener>();
		
		enabled = true;
		casting = false;
		
		castRoot = new Root();
		
		indicator = new CastingIndicator();
		
		addActiveAbility(new HomingProjectileAbility());
		addActiveAbility(new FreeProjectileAbility());

		castRoot.setLifetime(Lifetime.FOREVER);
	}
	
	public void addActiveAbility(ActiveAbility active)
	{
		if(parent != null)
			active.setSrc(parent);
		
		actives.add(active);
	}
	
	public void cast(int i)
	{
		ActiveAbility ability =
				actives.get(i);
		
		cast(ability);
	}
	
	public void cast(ActiveAbility ability)
	{
		if(enabled && !casting && 
		   actives.contains(ability))
		{
			ability.cast();
			
			if(ability.isCasting())
			{
				addCastRoot();
				addCastingIndicator(ability);
			}
		}
	}
	@Override
	public void update(Duration delta)
	{				
		boolean _casting = false;
		
		for(ActiveAbility a : actives)
		{
			a.update(delta);
			
			if(a.isCasting())
				_casting = true;
		}
		
		if(casting && !_casting)
		{
			removeCastRoot();
			removeCastingIndicator();
		}
		
		casting = _casting;
	}
	
	private void addCastRoot()
	{
		parent.get(EffectComponent.class)
		  	  .add(castRoot);
	}
	
	private void addCastingIndicator(
			ActiveAbility ability)
	{
		indicator.setActiveAbility(ability);
		
		parent.get(GraphicsComponent.class)
			  .getDecorations()
			  .add(indicator, 0, -15);
	}
	
	public void addListener(AbilityListener list) {
		lists.add(list);
	}
	
	private void removeCastRoot()
	{
		parent.get(EffectComponent.class)
			  .remove(castRoot);
	}
	
	private void removeCastingIndicator()
	{
		parent.get(GraphicsComponent.class)
			  .getDecorations()
			  .remove(indicator);
	}
	
	public void removeListener(AbilityListener list) {
		lists.remove(list);
	}

	public void setAllActivesEnabled(boolean enabled)
	{
		for(ActiveAbility ability : actives)
			ability.setEnabled(enabled);
	}
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public Collection<ActiveAbility> getActives()
	{
		return actives;
	}
	
	public ActiveAbility getActive(int i)
	{
		return actives.get(i);
	}
	
	public HomingProjectileAbility getBasicAttack()
	{
		return (HomingProjectileAbility) actives.get(0);
	}
	
	public void setParent(Entity parent)
	{
		super.setParent(parent);
		
		for(ActiveAbility a : actives)
			a.setSrc(parent);
		
		castRoot.setTarget(parent);
	}

	private void readObject(java.io.ObjectInputStream in)
		     throws IOException, ClassNotFoundException
     {
		in.defaultReadObject();
						
		castRoot = new Root();		
     }
	 
	@Override
	protected EntityComponent _clone()
	{
		return null;
	}
		
	public String toString()
	{
		String str = super.toString();
		
		str += "\nActives: ";
		for(ActiveAbility active : actives)
			str += '\n' + active.toString();
		
		return str;
	}
}
