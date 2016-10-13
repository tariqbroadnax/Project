package Utilities;

import static java.lang.Math.*;

import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import Maths.Circle2D;

public class MathUtilities
{
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
