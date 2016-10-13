package AbilityInitiators;

import java.awt.geom.Point2D;

import Ability.AbilityEvent;
import Ability.AbilityInitiator;
import Graphic.Vector2D;
import Utilities.EntityUtilities;

public class InstantInitiator
	implements AbilityInitiator
{
	@Override
	public void abilityActivated(AbilityEvent e)
	{	
		Point2D.Double targetLoc = 
				EntityUtilities.getLoc(e.target),
					   casterLoc =
				EntityUtilities.getLoc(e.caster);
		
		Point2D.Double abilityLoc =
				new Point2D.Double(targetLoc.x, targetLoc.y);
		
		Vector2D.Double shift = 
				new Vector2D.Double(
						casterLoc.x - targetLoc.x,
						casterLoc.y - targetLoc.y)
				.getUnit();
		
		shift.move(abilityLoc);
		
		EntityUtilities.setLoc(e.abilityEntity, abilityLoc);
	}
}
