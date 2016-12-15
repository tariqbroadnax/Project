package Editor.layer_selector;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import javax.swing.UIManager;

import Graphic.Graphic;

public class BackgroundCellRenderer 
	implements ListCellRenderer<Graphic>
{
	private JLabel label;
	
	public BackgroundCellRenderer()
	{
		label = new JLabel();
		label.setOpaque(true);
	}
	
	@Override
	public Component getListCellRendererComponent(
			JList<? extends Graphic> list, Graphic value, int index,
			boolean isSelected, boolean cellHasFocus) 
	{
		label.setText("Layer" + index);
		
		if(isSelected)
		{
			Color color = new Color(200, 200, 255);

			label.setBackground(color);
		}
		else
		{
			label.setBackground(Color.white);
		}
		return label;
	}

}
