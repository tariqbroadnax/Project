package Editor.comp;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

public class ShapeGraphicForm extends Form
{
	public ShapeGraphicForm()
	{
		ShapeForm shapeForm = new ShapeForm();
		
		JLabel colorLbl = new JLabel("Color"),
			   filledLbl = new JLabel("Filled");
		
		ColorField colorField = new ColorField();
		
		JCheckBox filledBox = new JCheckBox();
	
		addField(shapeForm, 0, 0, 2);
		addComponent(colorLbl, 0, 1, 1);
		addField(colorField, 1, 1, 1);
		addComponent(filledLbl, 0, 2, 1);
		addComponent(filledBox, 1, 2, 1);
	}
}
