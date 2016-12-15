package Ability;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import Graphic.Graphic;
import Graphic.GraphicsContext;
import Maths.Dimension2D;

public class CastingIndicator extends Graphic
{
	private ActiveAbility ability;
	
	private Rectangle2D.Double back, front;
	
	public CastingIndicator()
	{
		ability = null;
		
		back = new Rectangle2D.Double();
		front = new Rectangle2D.Double();
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
				
		Point2D.Double drawloc = 
				gc.camera.screenLocation(loc.x - 10, loc.y);
		
		Dimension2D.Double size =
				gc.camera.sizeOnScreen(20, 2);
	
		back.x = front.x = drawloc.x;
		back.y = front.y = drawloc.y;
		back.width = size.width;
		front.width = size.width * ratio;
		back.height = front.height = size.height;
		
		gc.g2d.setColor(Color.BLACK);
		gc.g2d.fill(back);
		
		gc.g2d.setColor(Color.GREEN);
		gc.g2d.fill(front);
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
