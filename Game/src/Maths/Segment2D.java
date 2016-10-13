package Maths;

import java.awt.geom.Point2D;

import Utilities.MathUtilities;
import Utilities.Pack;

import static java.lang.Math.*;

public class Segment2D
{
	public static class Double
	{
		Point2D.Double pt1, pt2;
		
		public Double(Point2D.Double pt1, Point2D.Double pt2)
		{
			this.pt1 = pt1;
			this.pt2 = pt2;
		}
		
		public boolean intersects(Circle2D.Double circle)
		{
			Point2D.Double center = circle.getCenter();
			
			double x1 = pt1.x - center.x,
				   y1 = pt1.y - center.y,
				   x2 = pt2.x - center.x,
				   y2 = pt2.y - center.y;
			
			double xDelta = x2 - x1,
				   yDelta = y2 - y1;
			
			double A = pow(xDelta, 2) + pow(yDelta, 2),
				   B = 2 * (x1 * xDelta + y1 * yDelta),
				   C = pow(x1, 2) + pow(y1, 2) - pow(circle.radius, 2);
			
			Pack<java.lang.Double, java.lang.Double> solutions = 
					MathUtilities.quadraticFormula(A, B, C);
					
			Range.Double range = new Range.Double(0, 1);
						
			if(solutions == null)
				return false;
			else if(range.contains(solutions.head) || range.contains(solutions.tail))
				return true;
			else return false;
		}
		
		public String toString()
		{
			return "[(" + pt1.x + "," + pt1.y + ")," + "(" + pt2.x + "," + pt2.y + ")]";
		}
	}
}
