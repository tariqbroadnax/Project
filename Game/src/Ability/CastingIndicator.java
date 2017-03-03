package Ability;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import Graphic.Graphic;
import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;
import Maths.Dimension2D;

public class CastingIndicator extends Graphic
{
	private ActiveAbility ability;
		
	public CastingIndicator()
	{
		ability = null;
	}
	
	@Override
	protected void _paint(GraphicsContext gc)
	{	
		if(ability == null)
			return;
				
		double ratio = 
				ability.getElapsedSinceCast() * 1.0 /
				ability.getCastTime();
		
		ratio = ratio > 1 ? 1 : ratio;
	
		ShapeGraphic graph = new ShapeGraphic();
	
		Rectangle2D.Double rect = new Rectangle2D.Double(
				loc.x - 7.5, loc.y - 1.25, 15 * ratio, 2.5);
		
		graph.setShape(rect);
		graph.setPaint(Color.blue);
		
		graph.paint(gc);
	}
	
	public void setActiveAbility(
			ActiveAbility ability) {
		this.ability = ability;
	}

	@Override
	public Double getBound() {
		return null;
	}

	@Override
	public Object clone() {
		// TODO Auto-generated method stub
		return null;
	}
}
