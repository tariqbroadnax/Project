package Maths;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;

import Utilities.Pack;

import static java.lang.Math.*;

public class Maths 
{
	public static int len(int num)
	{
		return ("" + num).length();
	}
	
	public static Point2D.Double[] points(Rectangle2D.Double rect)
	{
		Point2D.Double[] points = new Point2D.Double[]
		{
			new Point2D.Double(rect.x, rect.y),
			new Point2D.Double(rect.x + rect.width, rect.y),
			new Point2D.Double(rect.x, rect.y + rect.height),
			new Point2D.Double(rect.x + rect.width, rect.y + rect.height)
		};
		
		return points;
	}
	
	public static boolean overlaps(
			Circle2D.Double circ,
			Rectangle2D.Double rect)
	{
		Vector2D.Double circDist =
				new Vector2D.Double(
						abs(circ.getCenterX() - rect.getCenterX()),
						abs(circ.getCenterY() - rect.getCenterY()));
	
		if(circDist.x > rect.width/2 + circ.radius)
			return false;
		if(circDist.y > rect.height/2 + circ.radius)
			return false;
		
		if(circDist.x <= rect.width/2)
			return true;
		if(circDist.y <= rect.height/2)
			return true;
		
		double cornerDistSq = pow((circDist.x - rect.width/2),2) +
				  			  pow((circDist.y - rect.height/2),2);
		
		return cornerDistSq <= circ.radius * circ.radius;
	}
	
	public static Rectangle2D.Double overlappingBound(
			Collection<Rectangle2D.Double> rects)
	{
		if(rects.size() == 0)
			return null;
		
		double minX = Double.MAX_VALUE,
			   minY = minX,
			   maxX = Double.MIN_VALUE,
			   maxY = maxX;
		
		for(Rectangle2D.Double rect : rects)
		{
			minX = rect.getMinX() < minX ? rect.getMinX() : minX;
			minY = rect.getMinY() < minY ? rect.getMinY() : minY;
			
			maxX = rect.getMaxX() > maxX ? rect.getMaxX() : maxX;
			maxY = rect.getMaxY() > maxY ? rect.getMaxY() : maxY;
			
		}
		
		return new Rectangle2D.Double(minX, minY, maxX - minX, maxY - minY);
	}
	
public static final int GREATER = 1;
	
	public static int flat(int i)
	{
		if(i > 0)
			return 1;
		else if(i < 0)
			return -1;
		else return 0;
	}
	
	public static boolean overlaps(
			Rectangle2D.Double rect1, Rectangle2D.Double rect2)
	{
		return rect1.x < rect2.x + rect2.width &&
			   rect1.x + rect1.width > rect2.x &&
			   rect1.y < rect2.y + rect2.height &&
			   rect1.y + rect1.height > rect2.y;
	}
	
	public static Pack<Double, Double> quadraticFormula(double A, double B, double C)
	{
		if(noQuadraticSolution(A, B, C)) 
			return null;
		else
			return new Pack<Double, Double>(
					(-B - sqrt(B*B - 4*A*C))/(2 * A),
					(-B + sqrt(B*B - 4*A*C))/(2 * A));
	}
	
	private static boolean noQuadraticSolution(double A, double B, double C)
	{
		return (2*A) == 0 || (B*B - 4*A*C) < 0;
	}
	
	public static double angleFrom(Point2D.Double pt1, Point2D.Double pt2)
	{
		return atan2(pt2.y - pt1.y, pt2.x - pt1.x);
	}
	
}
