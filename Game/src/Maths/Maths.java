package Maths;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Collection;

import static java.lang.Math.*;

import Graphic.Vector2D;

public class Maths 
{
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
}
