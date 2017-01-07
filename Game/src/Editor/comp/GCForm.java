package Editor.comp;

import javax.swing.JComboBox;
import javax.swing.JLabel;

public class GCForm extends Form
{
	public GCForm()
	{
		JLabel label = new JLabel("Graphic: ");
	
		String[] graphStrs = 
			{"Sprite", "Shape", "Text", "Line",
			 "Animation", "Layered"};
		
		JComboBox<String> graphBox = new JComboBox<String>(graphStrs);
		
		addComponent(label, 0, 0);
		addComponent(graphBox, 1, 0);
	}
}
