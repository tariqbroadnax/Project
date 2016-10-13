package AbilityInitiators;

import java.awt.geom.Point2D;

import Ability.AbilityEvent;
import Ability.AbilityInitiator;
import EntityComponent.StatsComponent;
import Graphic.Vector2D;
import Movement.LooseMovement;
import Movement.MovementComponent;
import Stat.CoreStatType;
import Stat.Stats;
import Utilities.MathUtilities;

public class ProjectileInitiator implements AbilityInitiator
{
	private double offsetMagnitude;
	
	public ProjectileInitiator()
	{
		offsetMagnitude = 15;
	}
	
	@Override
	public void abilityActivated(AbilityEvent e)
	{
		Point2D.Double casterLoc = e.caster.getLoc();
		
		double angle = MathUtilities.angleFrom(
					 casterLoc, e.loc);

		Vector2D.Double offset = new Vector2D.Double(
				offsetMagnitude * Math.cos(angle),
				offsetMagnitude * Math.sin(angle));
	
		Point2D.Double startLoc = 
				offset.getMoved(casterLoc);
				
		LooseMovement movement =
			(LooseMovement)
			e.abilityEntity
					 .get(MovementComponent.class)
					 .getNormalMovement();
		
		movement.setDirection(angle);
				
		Stats stats = 
			e.abilityEntity
				     .get(StatsComponent.class)
					 .getStats();
		
		
		e.abilityEntity.setLoc(startLoc);
		stats.setCoreStatValue(CoreStatType.SPEED, 50);
	}	
}
