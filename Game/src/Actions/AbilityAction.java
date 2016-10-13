package Actions;

import java.awt.Point;
import java.awt.geom.Point2D;

import EntityComponent.AbilityComponent;
import GameClient.GameClientResources;

public class AbilityAction extends GameAction
{	
	private int abilityIndex;
	
	private Point2D.Double targetLoc;
	
	public AbilityAction(GameClientResources resources)
	{
		super(resources);
		
		abilityIndex = 1;
	}

	@Override
	public void executeOnClient()
	{
		targetLoc = resources
				.getCamera()
				.screenLocToNormalLoc(mouseLoc);
		
		resources.getPlayer()
				 .get(AbilityComponent.class)
				 .castActiveAbility(abilityIndex, targetLoc);
				
		targetLoc = null;		
	}
}
