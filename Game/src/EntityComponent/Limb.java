package EntityComponent;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.io.Serializable;

import Graphic.Vector2D;

public class Limb implements Serializable, Cloneable
{
	private RectangularShape shape;

	private Vector2D.Double offset;
	
	public Limb()
	{
		shape = new Rectangle2D.Double();

		offset = new Vector2D.Double();		
	}
	
	public Limb(RectangularShape shape)
	{
		this(shape, new Vector2D.Double());
	}
	
	public Limb(RectangularShape shape, Vector2D.Double offset)
	{
		this.shape = shape;
		
		this.offset = offset;
	}
	
	public Limb(Limb limb)
	{
		shape = (RectangularShape) limb.shape.clone();
		
		offset = (Vector2D.Double) limb.offset.clone();
	}
	
	public void setShape(RectangularShape shape) {
		this.shape = shape;
	}
	
	public void setOffset(Vector2D.Double offset) {
		this.offset = offset;
	}
	
	public RectangularShape getShape() {
		return shape;
	}
	
	public Vector2D.Double getOffset() {
		return offset;
	}
	
	public Object clone() {
		return new Limb(this);
	}
}
