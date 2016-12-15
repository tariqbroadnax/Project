package Graphic;

import java.awt.geom.Point2D;
import java.io.Serializable;

import Maths.Vector2D;

public class Layer implements Serializable, Cloneable
{
	public Graphic graphic;
	
	public Vector2D.Double offset;
	
	public Layer(Graphic graphic) 
	{
		this(graphic, 0, 0);
	}
	
	public Layer(Graphic graphic, Vector2D.Double offset)
	{
		this(graphic, offset.x, offset.y);
	}
	
	public Layer(Graphic graphic, double offsetX, double offsetY)
	{
		this.graphic = graphic;
		
		offset = new Vector2D.Double(offsetX, offsetY);
	}
	
	public Layer(Layer layer)
	{
		graphic = (Graphic)layer.graphic.clone();
		
		offset = new Vector2D.Double(layer.offset);
	}
	
	public void setLoc(Point2D.Double loc) 
	{
		Point2D.Double newLoc = offset.getMoved(loc);
		
		graphic.setLoc(newLoc);
	}
	
	public Layer clone() {
		return new Layer(this);
	}
}
