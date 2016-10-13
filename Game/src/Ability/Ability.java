package Ability;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import EntityComponent.MetaComponent;
import Game.Entity;

public abstract class Ability
implements Serializable
{
	private transient Collection<Entity> abilityEntities;
	
	private transient Map<Entity, LinkedList<AbilityInitiator>> initiators;	
		
	protected transient AbilityEvent event;
	
	protected String name;
	
	protected int iconID;
	
	public Ability()
	{
		abilityEntities = new LinkedList<Entity>();
		
		initiators = new HashMap<Entity, LinkedList<AbilityInitiator>>();		
	}
	
	public Ability(Ability ability)
	{
		this();
		
		for(Entity e : ability.abilityEntities)
		{
			addAbilityEntity(e);
		
			for(AbilityInitiator initiator : ability.initiators.get(e))
				addAbilityInitiator(e, initiator);
		}		
		
		name = ability.name;
		
		iconID = ability.iconID;
	}
	
	public void addAbilityEntity(Entity ability)
	{
		abilityEntities.add(ability);
	}
	
	public void addAbilityEntity(Entity e, AbilityInitiator... initiators)
	{
		abilityEntities.add(e);
		
		this.initiators.put(e, new LinkedList<AbilityInitiator>());
		
		for(int i = 0; i < initiators.length; i++)
			this.initiators.get(e).add(initiators[i]);
	}
	
	public void addAbilityInitiator(Entity e, AbilityInitiator listener)
	{
		if(initiators.get(e) == null)
			initiators.put(e, new LinkedList<AbilityInitiator>());
		
		initiators.get(e).add(listener);
	}
	
	public Collection<Entity> createAbilityEntities(Entity caster)
	{
		Collection<Entity> createdEntities =
				new LinkedList<Entity>();
		
		Entity entity;
		AbilityEvent e;
		for(Entity modelEntity : abilityEntities)
		{
			entity = new Entity(modelEntity);
			
			event.abilityEntity = entity;
			
			for(AbilityInitiator initiator : initiators.get(modelEntity))
				initiator.abilityActivated(event);
			
			entity.get(MetaComponent.class)
				  .setCreator(caster);
			
			createdEntities.add(entity);
		}
		
		event = null;
		return createdEntities;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}
	
	public void setIconID(int iconID)
	{
		this.iconID = iconID;
	}
	
	public String getName()
	{
		return name;
	}
	
	public int getIconID()
	{
		return iconID;
	}
	
	public String toString()
	{
		String str = super.toString();
		
		str += "\nAbility Entities: ";
		for(Entity abilityEntity : abilityEntities)
		{
			str += '\n' + abilityEntity.toString2();
			str += "\nAbility Intiators: ";
			
			for(AbilityInitiator initiator : initiators.get(abilityEntity))
				str += '\n' + initiator.toString();
		}
		
		return str;
	}
}
