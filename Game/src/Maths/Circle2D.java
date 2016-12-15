package Maths;

import static java.lang.Math.*;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class Circle2D
{
	@SuppressWarnings("serial")
	public static class Double extends Ellipse2D.Double
	{				
		public double radius;
		
		public Double()
		{
			super(0, 0, 20, 20);
			
			radius = 10;
		}
		
		public Double(Circle2D.Double circ)
		{
			this(circ.getX(), circ.getY(), circ.radius);
		}

		public Double(double x, double y, double radius)
		{
			super(x, y, 2 * radius, 2 * radius);
			
			this.radius = radius;
		}
		
		public boolean contains(Point2D.Double pt)
		{
			return getCenter().distanceSq(pt)
				   < radius * radius;
		}
		
		public double diam()
		{
			return 2 * radius;
		}

		public Point2D.Double randomPoint()
		{
			double angle = 2 * PI * random(),
				   rad = radius * sqrt(random());
			
			return new Point2D.Double(
					getCenterX() + rad * cos(angle),
					getCenterY() + rad * sin(angle));
		}
		
		public Point2D.Double getCenter()
		{
			return new Point2D.Double(getCenterX(), getCenterY());
		}
		
		public void setRadius(double radius)
		{
			this.radius = radius;
			this.setFrame(x, y, 2 * radius, 2 * radius);
		}
		
		public double getRadius()
		{
			return radius;
		}
		
		public String toString()
		{
			return "[x=" + x + ",y=" + y + ",r=" + radius + "]";
		}
	}
}
