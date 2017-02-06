package Editor.comp;

import java.awt.Component;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import Graphic.Animation;
import Graphic.Graphic;
import Graphic.LayeredGraphic;
import Graphic.LineGraphic;
import Graphic.ShapeGraphic;
import Graphic.Sprite;
import Graphic.TextGraphic;

public class GraphicChooser extends JComboBox<Class<? extends Graphic>>
	implements ListCellRenderer<Class<? extends Graphic>> 
{
	public GraphicChooser()
	{
		DefaultComboBoxModel<Class<? extends Graphic>> model = 
				new DefaultComboBoxModel<Class<? extends Graphic>>();
		
		model.addElement(Sprite.class);
		model.addElement(ShapeGraphic.class);
		model.addElement(TextGraphic.class);
		model.addElement(LineGraphic.class);
		model.addElement(Animation.class);
		model.addElement(LayeredGraphic.class);
		
		setModel(model);
		setRenderer(this);
	}


	JLabel label = new JLabel();
	
	@Override
	public Component getListCellRendererComponent(
			JList<? extends Class<? extends Graphic>> list,
			Class<? extends Graphic> value, int index,
			boolean selected, boolean focused) 
	{
		if(value.equals(Sprite.class))
			label.setText("Sprite");
		else if(value.equals(ShapeGraphic.class))
			label.setText("Shape");
		else if(value.equals(TextGraphic.class))
			label.setText("Text");
		else if(value.equals(LineGraphic.class))
			label.setText("Line");
		else if(value.equals(Animation.class))
			label.setText("Animation");
		else if(value.equals(LayeredGraphic.class))
			label.setText("Layered");
	
		
		return label;
	}
}
