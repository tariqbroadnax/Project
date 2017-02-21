package Graphic;

import static java.lang.Math.atan2;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class Vector2D
	implements Serializable, Cloneable
{
	public static class Double extends Vector2D
	{
		public double x, y;
		
		public Double()
		{
			x = y = 0;
		}
		
		public Double(double x, double y)
		{
			this.x = x;
			this.y = y;
		}
		
		public static Vector2D.Double vectorOf(double angle, double magnitude)
		{
			return new Vector2D.Double(
					 magnitude * Math.cos(angle),
					 magnitude * Math.sin(angle));
		}
		
		public Double(Double v)
		{
			x = v.x;
			y = v.y;
		}

		public double angle()
		{
			return atan2(y, x);
		}
		
		public void move(Point2D.Double loc)
		{
			loc.setLocation(loc.x + x, loc.y + y);
		}
		
		public Point2D.Double getMoved(Point2D.Double loc)
		{
			return new Point2D.Double(loc.x + x, loc.y + y);
		}
		
		public double magnitudeSqrd()
		{
			return x * x + y * y;
		}
		
		public Vector2D.Double getScaled(double scalar)
		{
			return new Vector2D.Double(scalar * x, scalar * y);
		}
		
		public static Vector2D.Double direction(double angle)
		{
			return new Vector2D.Double(
					cos(angle), sin(angle));
		}
		
		public Vector2D.Double getUnit()
		{
			return direction(angle());
		}
		
		public Object clone() {
			return new Vector2D.Double(this);
		}
		
		public String toString()
		{
			return "<" + x + "," + y + ">";
		}
	}
}
