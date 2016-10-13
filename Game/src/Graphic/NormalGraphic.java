package Graphic;

import java.awt.Dimension;
import java.awt.geom.Point2D;

public interface NormalGraphic
{
	public static double findScreenWidth(double normalWidth, int screenWidth)
	{
		return normalWidth * screenWidth / 100;
	}
	
	public static double findScreenHeight(double normalHeight, int screenHeight)
	{
		return normalHeight * screenHeight / 100;
	}
	
	public static Dimension findScreenDimension(
			Dimension normalDim, Dimension screenDim)
	{
		return new Dimension(
				(int)findScreenWidth(normalDim.width, screenDim.width),
				(int)findScreenHeight(normalDim.height, screenDim.height));
	}
	
	public static Point2D.Double findScreenLoc(Point2D.Double loc, Dimension screenDim)
	{
		return new Point2D.Double(
				loc.x * screenDim.width / 100,
				loc.y * screenDim.height / 100);
	}
}
