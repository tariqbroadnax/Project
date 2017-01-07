package Editor.comp;

import javax.swing.JComboBox;
import javax.swing.JPanel;

public class ShapeForm extends Form
{
	public ShapeForm()
	{
		String[] shapeStrs = {"Rectangle", "Oval"};
		
		JComboBox<String> shapeBox = new JComboBox<String>(shapeStrs);
		
		DimForm dimForm = new DimForm();
		
		addComponent(shapeBox, 0, 0, 1);
		addField(dimForm, 0, 1, 1);

	}
}
