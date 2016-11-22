package EntityComponent;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

import Ability.AbilityEvent;
import Ability.ActiveAbility;
import Ability.FreeProjectileAbility;
import Ability.HomingProjectileAbility;
import Game.Entity;
import Modifiers.Root;
import TestEntity.BasicAttack;

public class AbilityComponent extends EntityComponent
{
	public static final int BASIC_ATTACK_INDEX = 0;
	
	private ArrayList<ActiveAbility> actives;
	
	private transient Queue<AbilityEvent> castQueue;
	
	private transient boolean casting;
	
	private transient Root castRoot;

	public AbilityComponent()
	{
		super();
		
		actives = new ArrayList<ActiveAbility>();
	
		casting = false;
		
		castQueue = new LinkedList<AbilityEvent>();
	
		addActiveAbility(new HomingProjectileAbility());
		
		castRoot = new Root();
		castRoot.setLifetime(Duration.ofDays(1));
	}
	
	public void addActiveAbility(ActiveAbility active)
	{
		if(parent != null)
			active.setSrc(parent);
		
		actives.add(active);
	}
	
	public void castActiveAbility(int index)
	{
		castActiveAbility(index, null, null);
	}
	
	public void castActiveAbility(int index, Entity target)
	{
		castActiveAbility(index, target, null);
	}
	
	public void castActiveAbility(int index, Point2D.Double loc)
	{
		castActiveAbility(index, null, loc);
	}
	
	private void castActiveAbility(int index, Entity target,
			Point2D.Double loc)
	{
		if(!casting)
		{
			castQueue.add(new AbilityEvent(
					actives.get(index),
					parent, target, loc));
		}
	}
	
	@Override
	public void update(Duration delta)
	{				
		// removeCastRoot();
		
		checkAndHandleCasts();
		
		updateAbilities(delta); // order is important

		// checkAndActivateAbilities();
		
		//if(casting)
			//addCastRoot();
	}
	
	private void addCastRoot()
	{
		parent.get(ModifierComponent.class)
		  .recieveModifier(castRoot);
	}
	
	private void removeCastRoot()
	{
		parent.get(ModifierComponent.class)
		  	  .removeRecievedModifier(castRoot);
	}

	public void setAllActivesEnabled(boolean enabled)
	{
		for(ActiveAbility ability : actives)
			ability.setEnabled(enabled);
	}
	
	private void updateAbilities(Duration delta)
	{
		for(ActiveAbility ability : actives)
			ability.update(delta);			
	}
	
	private void checkAndHandleCasts()
	{
		casting = false;
		
		for(ActiveAbility ability : actives)
			casting = ability.isCasting() ? true : casting;
		
		while(!(casting || castQueue.isEmpty()))
		{
			AbilityEvent event = castQueue.poll();
			
			ActiveAbility ability = (ActiveAbility) event.ability;
			
			// casting = ability.cast(event);
		}
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
	}

	private void readObject(java.io.ObjectInputStream in)
		     throws IOException, ClassNotFoundException
     {
		in.defaultReadObject();
		
		castQueue = new LinkedList<AbilityEvent>();
		
		actives.add(new BasicAttack());
		
		castRoot = new Root();
		castRoot.setLifetime(Duration.ofDays(1));
		
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
