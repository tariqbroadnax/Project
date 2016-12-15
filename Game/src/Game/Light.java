package Game;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

import Graphic.Camera;
import Maths.Circle2D;
import Maths.Maths;
import static java.lang.Math.*;

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
	
	public Light(double x, double y)
	{
		this();
		
		loc.x = x;
		loc.y = y;
	}

	public void paint(Camera camera, Graphics2D g2d )
	{
		if(Maths.overlaps(getBound(), camera.normalViewBound()))
		{
			Circle2D.Double screenCirc = screenBound(camera);
			//System.out.println(screenCirc);
			/*float[] dist = {0f, 1.0f};
		    Color[] colors = {color, new Color(255,255,255,0)};

		    RadialGradientPaint p =
		            new RadialGradientPaint(
		            		screenCirc.getCenter(),
		            		(float)screenCirc.radius,
		            		dist, colors);*/
		  
		    double r2 = screenCirc.radius * screenCirc.radius;
		    
		    Rectangle2D.Double rect =
		    		new Rectangle2D.Double(0, 0, 20, 20);
		    
		    Point2D.Double center = screenCirc.getCenter();
		    for(double x = screenCirc.x; x < screenCirc.x + screenCirc.width; x += 20)
		    	for(double y = screenCirc.x; y < screenCirc.y + screenCirc.height; y += 20)
		    	{
		    		double dist = pow(x - center.x, 2) + pow(y - center.y, 2);
		    		if(dist < r2)
		    		{
		    			Color color = new Color(255, 255, 255, (int)((1 - dist / r2) * 255));
		    			rect.x = x;
		    			rect.y = y;
		    			g2d.setPaint(color);
		    			g2d.fill(rect);
		    		}
		    	}
		    		
		   // g2d.setPaint(p);
		   // g2d.fill(screenCirc);
		}
	}
	
	public Circle2D.Double getBound()
	{
		return new Circle2D.Double(loc.x - radius, loc.y - radius, radius);
	}
	
	public Circle2D.Double screenBound(Camera camera)
	{
		Point2D.Double screenLoc = 
				camera.screenLocation(loc);
		
		double screenRad = 
				radius * camera.viewSize().height 
				       / 100.0;	
		
		return new Circle2D.Double(
				screenLoc.x - screenRad,
				screenLoc.y - screenRad,
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
