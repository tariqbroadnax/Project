package Editor;

import java.awt.geom.Point2D;

public class SnapSettings 
{
	private double width, height;
	
	public SnapSettings()
	{
		width = height = 10;
	}
	
	public Point2D.Double snapLoc(double x, double y) 
	{
		x = (Math.floor(x / width)) * width;
		y = (Math.floor(y / height)) * height;
		
		return new Point2D.Double(x, y);
	}
	
	public void setSnapWidth(double width) {
		this.width = width;
	}
	
	public void setSnapHeight(double height) {
		this.height = height;
	}
	
	public double getSnapWidth() {
		return width;
	}
	
	public double getSnapHeight() {
		return height;
	}
}
