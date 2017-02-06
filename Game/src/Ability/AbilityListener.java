package Ability;

import Entity.Entity;

public interface AbilityListener 
{
	public default void abilityCasted(
			ActiveAbility ability){}
	
	public default void entityKilled(Entity ent){}
}
