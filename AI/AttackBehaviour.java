package AI;

import java.util.Collection;

import EntityComponent.AbilityComponent;
import Game.Entity;
import Utilities.EntityUtilities;

public class AttackBehaviour extends Behaviour
{
	private double minRange;

	private Entity nearestPlayer;
	
	public AttackBehaviour()
	{
		minRange = 10;
	}
	
	@Override
	public boolean isValid(Collection<Entity> entities)
	{
		nearestPlayer = 
				EntityUtilities.nearestPlayer(
						EntityUtilities.getLoc(target), entities);
		
		return nearestPlayer != null && targetClose();
		
	}
	
	private boolean targetClose()
	{
		return EntityUtilities.
				distanceSq(target, nearestPlayer) < 
				minRange * minRange;
	}

	@Override
	protected void _apply()
	{
		target.get(AbilityComponent.class)
			  .castActiveAbility(
					  AbilityComponent.BASIC_ATTACK_INDEX,
					  nearestPlayer);
	}

	@Override
	protected void _revert(){}

	@Override
	protected Behaviour _clone()
	{
		return new AttackBehaviour();
	}

}
