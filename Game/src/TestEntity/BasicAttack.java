package TestEntity;

import java.time.Duration;

import Ability.ActiveAbility;
import AbilityInitiators.InstantInitiator;

public class BasicAttack extends ActiveAbility
{
	public BasicAttack()
	{
		super();
		
		setCastTime(Duration.ofMillis(250));
		setCooldown(Duration.ofSeconds(2));
	
		addAbilityEntity(new BasicAttackEntity(),
						 new InstantInitiator());
		
		setName("Basic Attack");
	}
}
