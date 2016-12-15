package EntityEditorGUI;

import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

public enum RectangularShapeType 
{
	RECTANGLE, ELLIPSE;
	
	public static RectangularShapeType typeOf(
			RectangularShape shape)
	{
		if(shape instanceof Rectangle2D)
			return RECTANGLE;
		else if(shape instanceof Ellipse2D)
			return ELLIPSE;
		else
			return null;
	}
	
	public RectangularShape instance()
	{
		switch(this) {
		case RECTANGLE:
			return new Rectangle2D.Double();
		case ELLIPSE:
			return new Ellipse2D.Double();
		default:
			return null;
		}
	}
}
