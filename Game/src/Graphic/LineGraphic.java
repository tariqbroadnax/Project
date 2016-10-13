package Graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;

public class LineGraphic extends Graphic
	implements NormalGraphic
{	
	private Point2D.Double pt1, pt2;
	
	private Color color;
	
	public LineGraphic()
	{
		this(new Point2D.Double(),
			 new Point2D.Double());
	}
	
	public LineGraphic(Point2D.Double pt1, Point2D.Double pt2)
	{
		pt1 = new Point2D.Double(pt1.x, pt1.y);
		pt2 = new Point2D.Double(pt2.x, pt2.y);
		
		color = Color.red;
	}
	
	@Override
	protected void _paint(GraphicsContext gc)
	{
		gc.g2d.setColor(color);
		
		gc.g2d.fill(new Line2D.Double(
				NormalGraphic.findScreenLoc(pt1, gc.screenDim),
				NormalGraphic.findScreenLoc(pt2, gc.screenDim)));
	}

	@Override
	protected Graphic _clone()
	{
		return new LineGraphic(pt1, pt2);
	}

	@Override
	public void paint(Point screenLoc, Dimension dim, Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Rectangle2D.Double getBound() 
	{
		return new Rectangle2D.Double(
				pt1.x, pt1.y,
				pt2.x - pt1.x,
				pt2.y - pt1.y);
	}
	
}
