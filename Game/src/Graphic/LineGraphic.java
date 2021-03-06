package Graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Paint;
import java.awt.Stroke;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class LineGraphic extends Graphic
{	
	/* NOTICE -- 
	 * Line2D not used b/c loc is used pt1 */
	private Point2D.Double pt1, pt2;
	
	private Paint paint;
	
	private Stroke stroke;
	
	public LineGraphic()
	{
		this(0, 0, 10, 10);
	}
	
	public LineGraphic(double x1, double y1, double x2, double y2)
	{
		pt1 = loc;
		
		pt1.x = x1; pt1.y = y1;
		pt2 = new Point2D.Double(x2, y2);
	
		paint = Color.BLACK;

		stroke = new BasicStroke(1f);
	}
	
	public LineGraphic(Point2D.Double pt1, Point2D.Double pt2)
	{
		this(pt1.x, pt1.y, pt2.x, pt2.y);
	}
	
	public LineGraphic(LineGraphic graph)
	{
		super(graph);
		
		pt1 = loc;
		pt1.x = graph.pt1.x;
		pt1.y = graph.pt1.y;
		
		loc.x = graph.loc.x;
		loc.y = graph.loc.y;
		
		pt2 = new Point2D.Double(graph.pt2.x, graph.pt2.y);
		
		paint = graph.paint;
		
		stroke = graph.stroke;
	}
	
	@Override
	protected void _paint(GraphicsContext gc)
	{
		gc.g2d.setPaint(paint);
		
		Point2D.Double scrPt1 = 
				gc.camera.screenLocation2D(pt1),
					   scrPt2 =
				gc.camera.screenLocation2D(pt2);
		
		Line2D.Double line = new Line2D.Double(
				scrPt1.x, scrPt1.y, scrPt2.x, scrPt2.y);

		gc.g2d.setStroke(stroke);
		gc.g2d.draw(line);		
	}
	
	public void setPt1(double x, double y) {
		pt1.x = x; pt1.y = y;
	}
	
	public void setPt1(Point2D.Double pt1) {
		this.pt1.x = pt1.x;
		this.pt1.y = pt1.y;
	}
	
	public void setPt2(double x, double y) {
		pt2.x = x; pt2.y = y;
	}
	
	public void setPt2(Point2D.Double pt2) {
		this.pt2.x = pt2.x;
		this.pt2.y = pt2.y;
	}
	
	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	
	public void setStroke(Stroke stroke) {
		this.stroke = stroke;
	}
	
	public Point2D.Double getPt1()
	{
		Point2D.Double pt1 = new Point2D.Double(
				this.pt1.x, this.pt1.y);
		
		return pt1;
	}
	
	public Point2D.Double getPt2()
	{
		Point2D.Double pt2 = new Point2D.Double(
				this.pt2.x, this.pt2.y);
		
		return pt2;
	}
	
	public Paint getPaint() {
		return paint;
	}
	
	public Stroke getStroke() {
		return stroke;
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
