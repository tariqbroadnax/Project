package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.io.Serializable;

import Graphic.Camera;
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
		
		color = new Color(255, 255, 255, 255);
	}
	

	public void paint(Camera camera, Graphics2D g2d )
	{
		if(Maths.overlaps(getBound(), camera.normalViewBound()))
		{
			Circle2D.Double screenCirc = screenBound(camera);
			
		    float[] dist = {0f, 1.0f};

		    
		    Color[] colors = {color, new Color(255,255,255,0)};

		    RadialGradientPaint p =
		            new RadialGradientPaint(
		            		screenCirc.getCenter(),
		            		(float)screenCirc.radius,
		            		dist, colors);
		    
		    g2d.setPaint(p);
		    g2d.fill(screenCirc);
		}
	}
	
	public Circle2D.Double getBound()
	{
		return new Circle2D.Double(loc.x, loc.y, radius);
	}
	
	public Circle2D.Double screenBound(Camera camera)
	{
		Point2D.Double screenLoc = 
				camera.screenLocation(loc);
		
		double screenRad = 
				radius * camera.viewSize().height 
				       / 100.0;	
		
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
