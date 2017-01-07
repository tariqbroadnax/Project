package Editor.comp;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class GForm extends Form
{
	public GForm()
	{		
		JLabel visLbl = new JLabel("Visible"),
			   zLbl = new JLabel("Z"),
			   graphLbl = new JLabel("Graphic");
		
		JCheckBox visBox = new JCheckBox();
		
		Integer[] zs = {1, 2, 3, 4, 5};
		
		JComboBox<Integer> zBox = new JComboBox<Integer>(zs);
		
		String[] graphStrs = 
			{"Sprite", "Shape", "Text", "Line",
			 "Animation", "Layered"};
		
		JComboBox<String> graphBox = new JComboBox<String>(graphStrs);
		
		ShapeGraphicForm form = new ShapeGraphicForm();
		
		Border border = BorderFactory.createLineBorder(Color.black);
		
		form.setBorder(border);
		
		addComponent(visLbl, 0, 0, 1);
		addComponent(visBox, 1, 0, 1);
		addComponent(zLbl, 0, 1, 1);
		addComponent(zBox, 1, 1, 1);
		addComponent(graphLbl, 0, 2, 1);
		addComponent(graphBox, 1, 2, 1);
		addField(form, 0, 3, 2);
	}
}
