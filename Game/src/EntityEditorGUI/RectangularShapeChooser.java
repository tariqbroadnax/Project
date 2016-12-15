package EntityEditorGUI;

import java.awt.geom.RectangularShape;

import javax.swing.JComboBox;

public class RectangularShapeChooser 
	extends JComboBox<RectangularShapeType>
{
	public RectangularShapeChooser()
	{
		this(RectangularShapeType.RECTANGLE);
	}
	
	public RectangularShapeChooser(
			RectangularShape shape)
	{
		this(RectangularShapeType.typeOf(shape)); 
	}
	
	private RectangularShapeChooser(
			RectangularShapeType type)
	{
		super(RectangularShapeType.values());
	
		setSelectedItem(type);
	}
	
	public void setShape(RectangularShape shape)
	{
		RectangularShapeType type = 
				RectangularShapeType.typeOf(shape);
		
		setSelectedItem(type);
	}
	
	public RectangularShape getShape()
	{
		RectangularShapeType type = 
		(RectangularShapeType)
				getSelectedItem();
		
		return type.instance();
	}
}
