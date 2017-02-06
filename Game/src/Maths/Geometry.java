package Maths;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

public class Geometry 
{
	private Geometry(){}
	
	public static Rectangle2D.Double rectangle(
			Point2D.Double corner1, Point2D.Double corner2)
	{
		double minX = Math.min(corner1.x, corner2.x),
			   minY = Math.min(corner1.y, corner2.y),
			   maxX = Math.max(corner1.x, corner2.x),
			   maxY = Math.max(corner1.y, corner2.y);
		
		return new Rectangle2D.Double(minX, minY, maxX - minX,
												  maxY - minY);
	}
}
