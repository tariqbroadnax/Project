package EntityEditorGUI;

import Graphic.AnimatedGraphic;
import Graphic.Graphic;
import Graphic.ImageGraphic;
import Graphic.LayeredGraphic;
import Graphic.LineGraphic;
import Graphic.ShapeGraphic;
import Graphic.TextGraphic;

public enum GraphicType 
{
	Shape, Text, Line,
	Image,
	Animated, Layered;
	
	public static GraphicType typeOfGraphic(Graphic graphic)
	{
		Class<?> c = graphic.getClass();
		
		if(c.equals(ShapeGraphic.class))
			return Shape;
		else if(c.equals(TextGraphic.class))
			return Text;
		else if(c.equals(LineGraphic.class))
			return Line;
		else if(c.equals(ImageGraphic.class))
			return Image;
		else if(c.equals(AnimatedGraphic.class))
			return Animated;
		else if(c.equals(LayeredGraphic.class))
			return Layered;
		else return null;
	}
	
	public GraphicEditor graphicEditor()
	{
		switch(this)
		{
		case Shape:
			return new ShapeGraphicEditor();
		case Text:
			return new TextGraphicEditor();
		case Line:
			return new LineGraphicEditor();
		case Image:
			return new ImageGraphicEditor();
		case Animated:
			return new AnimatedGraphicEditor();
		case Layered:
			return new LayeredGraphicEditor();
		}
		
		return null;
	}
}
