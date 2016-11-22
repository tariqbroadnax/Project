package Actions;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.geom.Point2D.Double;
import java.util.List;

import Ability.TargetUnitAbility;
import EntityComponent.AbilityComponent;
import EntityComponent.GraphicsComponent;
import Game.Entity;
import Game.GameResources;
import Graphic.LayeredGraphic;
import Graphic.ShapeGraphic;
import Maths.Circle2D;

public class CastTargetUnitAbility
	extends SyncGameAction 
	implements PlayerMouseInputHandler
{
	private LayeredGraphic decorations;
	
	private TargetUnitAbility ability;

	private ShapeGraphic rangeGraphic;
	
	public CastTargetUnitAbility(
			GameResources resources, int i) 
	{
		super(resources);

		ability = (TargetUnitAbility)
				resources.player
				  		 .get(AbilityComponent.class)
						 .getActive(i);	

		decorations = 
				resources.player
						 .get(GraphicsComponent.class)
						 .getDecorations();
	
		Circle2D.Double circ =
				new Circle2D.Double(0,0,ability.getRange());
		
		rangeGraphic = new ShapeGraphic();
		rangeGraphic.setFilled(false);
		rangeGraphic.setPaint(new Color(0, 0, 0, 120));
		rangeGraphic.setShape(circ);
		rangeGraphic.setStroke(new BasicStroke(5));
	}

	@Override
	public void invoke() 
	{
		decorations.addLayer(rangeGraphic);
		resources.manager.setHandler(this);
	}

	@Override
	public void invoke(Double normLoc) 
	{
		List<Entity> entities =
				resources.scene.entitiesVisibleAtLocation(normLoc);
		
		entities.removeIf(e -> e == resources.player);
		
		if(entities.size() > 0)
		{
			ability.setTarget(entities.get(0));
			ability.cast();
		}
	}

	@Override
	public void dispose() 
	{
		decorations.remove(rangeGraphic);
	}
}
