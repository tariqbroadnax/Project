package Utilities;

import static java.lang.Math.pow;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.Collection;
import java.util.LinkedList;

import Maths.Circle2D;
import Maths.Dimension2D;
import Maths.Segment2D;

public class ShapeUtilities
{
	public static Collection<Point2D.Double> findPoints(
			Rectangle2D.Double rect)
	{
		Collection<Point2D.Double> points = 
				new LinkedList<Point2D.Double>();
		
		CollectionUtilities.addAll(points,
				new Point2D.Double(rect.x, rect.y),
				new Point2D.Double(rect.x + rect.width, rect.y),
				new Point2D.Double(rect.x, rect.y + rect.height),
				new Point2D.Double(rect.x + rect.width, rect.y + rect.height));
		
		return points;
	}
	
	public static Point2D.Double[] findPointsArr(Rectangle2D.Double rect)
	{
		Point2D.Double[] points = new Point2D.Double[4];
		
		points[0] = new Point2D.Double(rect.x, rect.y);
		points[1] = new Point2D.Double(rect.x + rect.width, rect.y);
		points[2] = new Point2D.Double(rect.x, rect.y + rect.height);
		points[3] = new Point2D.Double(rect.x + rect.width, rect.y + rect.height);
		
		return points;
	}
	
	public static Collection<Segment2D.Double> findEdges(Rectangle2D.Double rect)
	{
		Point2D.Double[] points = findPointsArr(rect);
		
		Collection<Segment2D.Double> segments = 
				new LinkedList<Segment2D.Double>();
		
		CollectionUtilities.addAll(segments,
				new Segment2D.Double(points[0], points[1]),
				new Segment2D.Double(points[1], points[2]),
				new Segment2D.Double(points[2], points[3]),
				new Segment2D.Double(points[3], points[0]));
		
		return segments;
	}
	
	public static void moveShape(Point2D.Double loc, RectangularShape shape)
	{
		shape.setFrame(loc.x, loc.y, shape.getWidth(), shape.getHeight());
	}
	
	public static boolean collides(RectangularShape shape1, RectangularShape shape2)
	{
		if(shape1 instanceof Rectangle2D.Double)
		{
			Rectangle2D.Double rect1 = (Rectangle2D.Double)shape1;
			
			if(shape2 instanceof Rectangle2D.Double)
				return collides(rect1, (Rectangle2D.Double)shape2);
			else if(shape2 instanceof Circle2D.Double)
				return collides(rect1, (Circle2D.Double)shape2);
			else
				return false;
		}
		else if(shape1 instanceof Circle2D.Double)
		{
			Circle2D.Double circ1 = (Circle2D.Double) shape1;
			
			if(shape2 instanceof Rectangle2D.Double)
				return collides((Rectangle2D.Double)shape2, circ1);
			else if(shape2 instanceof Circle2D.Double)
				return collides(circ1, (Circle2D.Double)shape2);
			else
				return false;
		}
		else
			return false;
	}
	
	public static boolean collides(Rectangle2D.Double rect, Rectangle2D.Double rect2)
	{		
		return rect.x < rect2.x + rect.width &&
			   rect.x + rect.width > rect2.x &&
			   rect.y < rect2.y + rect2.height &&
			   rect.y + rect.height > rect2.y;
	}
	
	public static boolean collides(Rectangle2D.Double rect, Circle2D.Double circle)
	{
		if(rect.contains(circle.getCenter()))
			return true;
		
		for(Segment2D.Double edge : findEdges(rect))
			if(edge.intersects(circle))
				return true;
		return false;
	}
	
	public static boolean collides(Circle2D.Double circ1, Circle2D.Double circ2)
	{
		Point2D.Double center1 = circ1.getCenter(),
					   center2 = circ2.getCenter();
		
		return center1.distanceSq(center2) <
			   pow(circ1.radius + circ2.radius, 2);
	}
	
	public static Dimension2D.Double scaleToFit(
			Dimension2D.Double target, double boundSideLength)
	{
		double aspectRatio = target.width / target.height;
		
		double scaledWidth, scaledHeight;
		if(aspectRatio > 0)
		{
			scaledWidth = boundSideLength;
			scaledHeight = scaledWidth / aspectRatio;
		}
		else
		{
			scaledHeight = boundSideLength;
			scaledWidth = scaledHeight * aspectRatio;
		}
	
		Dimension2D.Double scaledTarget = 
				new Dimension2D.Double(
						scaledWidth, scaledHeight);
		
		return scaledTarget;
	}
	
	public static Dimension2D.Double dimension(
			Rectangle2D.Double rect)
	{
		Dimension2D.Double dim = new Dimension2D.Double(
				rect.width, rect.height);
		
		return dim;
	}
	
	public static Point2D.Double offsetToCenter(
			Dimension2D.Double target, Dimension2D.Double bound)
	{
		Point2D.Double centerLoc =
				new Point2D.Double(
						(target.width - bound.width) / 2,
						(target.height - bound.height) /2);
		
		return centerLoc;
	}
}
