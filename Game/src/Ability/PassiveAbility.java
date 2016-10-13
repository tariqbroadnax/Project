package Ability;

import java.util.Collection;
import java.util.LinkedList;

import Game.Entity;

public class PassiveAbility extends Ability
{	
	private boolean created = false;
	
	private Collection<Entity> abilityEntities;
	
	public PassiveAbility()
	{
		created = false;
		
		abilityEntities = new LinkedList<Entity>();
	}
	
	@Override
	public Collection<Entity> getAbilityEntities(Entity src)
	{
		return null;
	}
	
	public void dispose()
	{
		
	}
}
