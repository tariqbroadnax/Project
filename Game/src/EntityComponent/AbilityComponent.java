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
import Game.Entity;
import Modifiers.Root;
import Stat.CoreStatType;
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
	
		actives.add(new BasicAttack());
		
		castRoot = new Root();
		castRoot.setLifetime(Duration.ofDays(1));
	}
	
	public AbilityComponent(AbilityComponent comp)
	{
		this();
		
		for(int i = BASIC_ATTACK_INDEX + 1;
				i < comp.actives.size(); i++)
			actives.add(
				new ActiveAbility(comp.actives.get(i)));
	}
	
	public void addActiveAbility(ActiveAbility active)
	{
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
		updateBasicAtkSpeed();
		
		removeCastRoot();
		
		checkAndHandleCasts();
		
		updateAbilities(delta); // order is important

		checkAndActivateAbilities();
		
		if(casting)
			addCastRoot();
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

	private void updateBasicAtkSpeed()
	{		
		int atkSpeed = parent
				.get(StatsComponent.class)
				.getStats()
				.getValue(CoreStatType.ATK_SPEED);
		
		ActiveAbility basicAttack =
				actives.get(BASIC_ATTACK_INDEX);
		
		basicAttack
			.setCastTime(Duration.ofMillis((long)(250 * atkSpeed)));
		
		basicAttack
			.setCooldown(Duration.ofMillis((long)(1000 * atkSpeed)));
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
			
			casting = ability.cast(event);
		}
	}
	
	private void checkAndActivateAbilities()
	{
		SpawnComponent comp =
				parent.get(SpawnComponent.class);
		
		for(ActiveAbility ability : actives)
			if(ability.hasBeenActivated())
			{				
				for(Entity abilityEntity : ability.createAbilityEntities(parent))
					comp.addChildren(abilityEntity);
			}
	}
	
	public Collection<ActiveAbility> getActives()
	{
		return actives;
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
		return new AbilityComponent(this);
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
