package Graphic;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.IOException;

public class ShapeGraphic extends Graphic
{	
	private RectangularShape shape, screenShape;
	
	private Paint paint;
	private boolean filled;
	
	private transient BasicStroke stroke;
	
	public ShapeGraphic()
	{
		super();
		
		filled = true;
		
		paint = Color.black;
		
		stroke = new BasicStroke(2.0f);
				
		shape = new Rectangle2D.Double(0, 0, 10, 10);
		screenShape = (RectangularShape) shape.clone();
	}
	
	public ShapeGraphic(ShapeGraphic graphic)
	{
		super(graphic);
		
		filled = graphic.filled;
		
		paint = graphic.paint;
	
		stroke = graphic.stroke;

		shape = (RectangularShape) graphic.shape.clone();
		screenShape = (RectangularShape) shape.clone();
	}
	
	@Override
	protected void _paint(GraphicsContext gc) 
	{		
		double w = shape.getWidth(),
			   h = shape.getHeight();
		
		shape.setFrame(loc.x - w/2, loc.y - h/2, w, h);
		
		gc.camera.screenShape(shape, screenShape);

		gc.g2d.setPaint(paint);
		
		paintShape(gc.g2d, screenShape);
	}
	
	private void paintShape(Graphics2D g2d, RectangularShape shape)
	{
		g2d = (Graphics2D)g2d.create();
		
		g2d.setPaint(paint);
		
		if(stroke != null)
			g2d.setStroke(stroke);
	
		if(filled)
			g2d.fill(shape);
		else
			g2d.draw(shape);
		
		g2d.dispose();
	}
	
	public void setStroke(BasicStroke stroke) {
		this.stroke = stroke;
	}
	
	public void setPaint(Paint paint) {
		this.paint = paint;
	}

	public void setShape(RectangularShape shape)
	{
		this.shape = (RectangularShape)shape.clone();
		screenShape = (RectangularShape)shape.clone();	 
	
		double centerX = shape.getCenterX(),
			   centerY = shape.getCenterY();
	
		setLoc(centerX, centerY);
	}
	
	public void setFilled(boolean filled) {
		this.filled = filled;
	}

	public boolean isFilled() {
		return filled;
	}
	
	public RectangularShape getShape() {
		return shape;
	}
	
	public BasicStroke getStroke() {
		return stroke;
	}

	public Paint getPaint() {
		return paint;
	}

	@Override
	public Rectangle2D.Double getBound() 
	{
		double w = shape.getWidth(),
			   h = shape.getHeight();
		
		return new Rectangle2D.Double(
				loc.x - w/2,
				loc.y - h/2, w, h);
	}
	
	@Override
	public Object clone() {
		return new ShapeGraphic(this);
	}
	
	private void readObject(java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException
    {
		in.defaultReadObject();
		stroke = new BasicStroke(2.0f);
    }
}