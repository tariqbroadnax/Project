package Graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.Shape;
import java.awt.Stroke;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public class ShapeGraphic extends Graphic
{
	private boolean filled;
	
	private Paint paint;
	
	protected RectangularShape shape;
	
	private transient BasicStroke stroke;
	
	public ShapeGraphic()
	{
		super();
		
		filled = true;
		
		paint = Color.black;
		
		stroke = new BasicStroke(1.0f);
		
		shape = new Rectangle2D.Double(0, 0, 10, 10);
	}
	
	public ShapeGraphic(ShapeGraphic graphic)
	{
		super(graphic);
		
		filled = graphic.filled;
		
		paint = graphic.paint;
		
		stroke = graphic.stroke;
		
		shape = (RectangularShape) graphic.shape.clone();
	}
	
	@Override
	protected void _paint(GraphicsContext gc) 
	{
		// TODO Auto-generated method stub
		
		Dimension viewDim = gc.camera.getViewDimension();
		
		double w = viewDim.width,
			   h = viewDim.height;
		
		RectangularShape shape = (RectangularShape) this.shape.clone();
		
		shape.setFrame(loc.x * w / 100, 
					   loc.y * h / 100,
					   this.shape.getWidth() * w / 100,
					   this.shape.getHeight() * h / 100);
	
		gc.g2d.setPaint(paint);
		
		drawShape(gc.g2d, shape);
	}

	@Override
	public void paint(
			Graphics2D g2d, Point screenLoc,
			Dimension projection) 
	{
		RectangularShape shape = (RectangularShape) this.shape.clone();

		shape.setFrame(screenLoc.x, screenLoc.y,
					  projection.width,
					  projection.height);
	
		g2d.setPaint(paint);

		drawShape(g2d, shape);
	}
	
	private void drawShape(Graphics2D g2d, RectangularShape shape)
	{
		g2d.setPaint(paint);
		
		if(stroke != null)
			g2d.setStroke(stroke);
	
		if(filled)
			g2d.fill(shape);
		else
			g2d.draw(shape);
	}
	
	@Override
	protected Graphic _clone()
	{
		return new ShapeGraphic(this);
	}

	public void setStroke(BasicStroke stroke)
	{
		this.stroke = stroke;

		notifyListenerOfFieldChanged();
	}
	
	public void setPaint(Paint paint)
	{
		this.paint = paint;
		
		notifyListenerOfFieldChanged();
	}
	
	public Paint getPaint()
	{
		return paint;
	}
	
	public void setShape(RectangularShape shape)
	{
		this.shape = shape;
		
		setLoc(loc);
		
		notifyListenerOfFieldChanged();
	}
	
	public void setLoc(Point2D.Double loc)
	{
		super.setLoc(loc);
		
		shape.setFrame(loc.x, loc.y, shape.getWidth(), shape.getHeight());
	
		notifyListenerOfFieldChanged();
	}
	
	public boolean isFilled()
	{
		return filled;
	}
	
	public RectangularShape getShape()
	{
		return shape;
	}
	
	public BasicStroke getStroke()
	{
		return stroke;
	}

	@Override
	public java.awt.geom.Rectangle2D.Double getBound() 
	{
		return (java.awt.geom.Rectangle2D.Double) shape.getBounds2D();
	}

	public void setFilled(boolean filled) 
	{
		this.filled = filled;
		
		notifyListenerOfFieldChanged();
	}

}
