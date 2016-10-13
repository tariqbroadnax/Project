package Graphic;

import java.awt.geom.Point2D;
import java.io.Serializable;

public class GraphicLayerRecord 
	implements Serializable, Cloneable
{
	public Graphic graphic;
	
	public Vector2D.Double offset;
	
	public GraphicLayerRecord(Graphic graphic, Vector2D.Double offset)
	{
		this.graphic = graphic;
		this.offset = offset;
	}
	
	public GraphicLayerRecord(GraphicLayerRecord record)
	{
		graphic = record.graphic._clone();
		
		offset = new Vector2D.Double(record.offset);
	}
	
	public void setLoc(Point2D.Double loc)
	{
		graphic.setLoc(offset.getMoved(loc));
	}
	
	public GraphicLayerRecord clone()
	{
		return new GraphicLayerRecord(this);
	}
}
