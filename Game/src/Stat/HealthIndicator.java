package Stat;

import java.awt.Color;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import Graphic.Graphic;
import Graphic.GraphicsContext;
import Maths.Dimension2D;

public class HealthIndicator extends Graphic
{
	private Stats stats;
	
	private Rectangle2D.Double back, front;

	public HealthIndicator()
	{
		stats = null;
		
		back = new Rectangle2D.Double();
		front = new Rectangle2D.Double();
	}
	
	public HealthIndicator(HealthIndicator ind)
	{
		stats = ind.stats;
		
		back = (Rectangle2D.Double)ind.back.clone();
		front = (Rectangle2D.Double)ind.front.clone();

	}
	
	@Override
	protected void _paint(GraphicsContext gc)
	{	
		if(stats == null)
			return;
				
		double ratio = 
				stats.getHealth() * 1.0 /
				stats.getMaxHealth();
		
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
		
		gc.g2d.setColor(Color.RED);
		gc.g2d.fill(front);
	}
	
	public void setStats(Stats stats) {
		this.stats = stats;
	}

	@Override
	public Double getBound() {
		// FIXME
		return null;
	}

	@Override
	public Object clone() {
		return new HealthIndicator(this);
	}
}
