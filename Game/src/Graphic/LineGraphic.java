package Graphic;

import java.awt.Color;
import java.awt.Paint;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class LineGraphic extends Graphic
{	
	private Point2D.Double pt1, pt2;
	
	private Paint paint;
	
	private Line2D.Double line;
	
	public LineGraphic()
	{
		this(0, 0, 0, 0);
	}
	
	public LineGraphic(double x1, double y1, double x2, double y2)
	{
		pt1 = new Point2D.Double(x1, y1);
		pt2 = new Point2D.Double(x2, y2);
	
		paint = Color.BLACK;
	
		line = new Line2D.Double();
	}
	
	public LineGraphic(LineGraphic graph)
	{
		pt1 = new Point2D.Double(graph.pt1.x, graph.pt1.y);
		pt2 = new Point2D.Double(graph.pt2.x, graph.pt2.y);
		
		paint = graph.paint;
		
		line = new Line2D.Double();
	}
	
	public LineGraphic(Point2D.Double pt1, Point2D.Double pt2)
	{
		this(pt1.x, pt1.y, pt2.x, pt2.y);
	}
	
	@Override
	protected void _paint(GraphicsContext gc)
	{
		gc.g2d.setPaint(paint);
		
		Point2D.Double scrPt1 = 
				gc.camera.screenLocation(pt1),
					   scrPt2 =
				gc.camera.screenLocation(pt2);
		
		line.x1 = scrPt1.x; line.y1 = scrPt1.y;
		line.x2 = scrPt2.x; line.y2 = scrPt2.y;
		
		gc.g2d.fill(line);
	}

	@Override
	public Rectangle2D.Double getBound() 
	{
		return new Rectangle2D.Double(
				pt1.x, pt1.y,
				pt2.x - pt1.x,
				pt2.y - pt1.y);
	}
	
	@Override
	public Object clone() {
		return new LineGraphic(this);
	}
}
