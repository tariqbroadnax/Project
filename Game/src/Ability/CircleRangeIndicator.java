package Ability;

import java.awt.Color;
import java.awt.geom.Point2D.Double;

import Graphic.ShapeGraphic;
import Maths.Circle2D;

public class CircleRangeIndicator 
	extends RangeIndicator
{
	private Circle2D.Double circle;

	public CircleRangeIndicator()
	{
		this(10);
	}
	

	public CircleRangeIndicator(double range)
	{
		circle = new Circle2D.Double(0, 0, range);
		
		graphic = new ShapeGraphic();

		((ShapeGraphic)graphic).setShape(circle);
		((ShapeGraphic)graphic).setFilled(false);
		((ShapeGraphic)graphic).setPaint(Color.orange);
	}
	
	public void setRange(double range)
	{
		circle.setRadius(range);

		((ShapeGraphic)graphic).setShape(circle);
	}

	@Override
	public void notifyMouseLoc(Double normLoc) {}
}
