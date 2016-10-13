package Game;

import java.awt.Color;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.io.Serializable;

import Graphic.GraphicsContext;
import Maths.Circle2D;
import Maths.Maths;

public class Light implements Serializable
{	
	private Point2D.Double loc;
	
	private double radius;

	private Color color;
	
	public Light()
	{
		loc = new Point2D.Double(0, 0);
		
		radius = 50;
		
		color = new Color(255, 255, 255, 120);
	}
	
	public void paint(GraphicsContext gc, Color ambientColor)
	{
		if(Maths.overlaps(getBound(), gc.viewBound))
		{
			Circle2D.Double screenCirc = screenBound(gc);
			
		    float[] dist = {0.0f, 1.0f};

		    Color[] colors = {color, ambientColor};

		    RadialGradientPaint p =
		            new RadialGradientPaint(
		            		screenCirc.getCenter(),
		            		(float)screenCirc.radius,
		            		dist, colors);
		    
		    gc.g2d.setPaint(p);
		    gc.g2d.fill(screenCirc);
		}
	}
	
	public Circle2D.Double getBound()
	{
		return new Circle2D.Double(loc.x, loc.y, radius);
	}
	
	public Circle2D.Double screenBound(GraphicsContext gc)
	{
		Point2D.Double screenLoc = gc.screenLoc(loc);
		
		double screenRad = radius * gc.viewDim.height / 100.0;	
		
		return new Circle2D.Double(
				screenLoc.x, screenLoc.y,
				screenRad);
	}
	
	public void setLocation(Point2D.Double loc)
	{
		this.loc.x = loc.x;
		this.loc.y = loc.y;
	}
	
	public void setRadius(double radius)
	{
		this.radius = radius;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
}
