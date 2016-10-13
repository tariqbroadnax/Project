package Ability;

import java.awt.geom.Point2D;

import Game.Entity;

public class AbilityEvent
{	
	public final Ability ability;
	
	public final Point2D.Double loc;
	
	public final Entity caster,
						target;
	
	public Entity abilityEntity;
	
	public AbilityEvent(
			Ability ability,
			Entity caster,
			Entity target,
			Point2D.Double loc)
	{
		this.ability = ability;
		this.caster = caster;
		this.target = target;
		this.loc = loc;
	}
}
