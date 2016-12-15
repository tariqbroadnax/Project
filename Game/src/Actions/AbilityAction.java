package Actions;

import Ability.TargetAbility;
import Ability.TargetSourceAbility;
import EntityComponent.AbilityComponent;
import Game.GameResources;

public class AbilityAction extends SyncGameAction
{
	private TargetAbility ability;
	
	public AbilityAction(GameResources resources, int i) 
	{
		super(resources);
	
		ability = (TargetAbility)
				resources.player
						 .get(AbilityComponent.class)
						 .getActive(i);
	}

	@Override
	public void invoke() 
	{
		if(ability instanceof TargetSourceAbility)
		{
			TargetSourceAbility ability = 
					(TargetSourceAbility)this.ability;
			
			resources.player
			 	.get(AbilityComponent.class)
			 	.cast(ability);
		}
		else
			resources.miHandler.prepareToCast(ability);
	}	
}
