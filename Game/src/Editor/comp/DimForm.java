package Editor.comp;

import javax.swing.JLabel;

import Editor.DoubleField;

public class DimForm extends Form
{
	public DimForm()
	{
		JLabel widthLbl = new JLabel("Width"),
			   heightLbl = new JLabel("Height");
		
		DoubleField widthFld = new DoubleField(),
					heightFld = new DoubleField();
		
		addComponent(widthLbl, 0, 0, 1);
		addField(widthFld, 1, 0, 1);
		addComponent(heightLbl, 0, 1, 1);
		addField(heightFld, 1, 1, 1);
	}
}
