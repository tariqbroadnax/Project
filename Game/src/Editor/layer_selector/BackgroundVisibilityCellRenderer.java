package Editor.layer_selector;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import Graphic.Graphic;

public class BackgroundVisibilityCellRenderer 
	implements ListCellRenderer<Graphic>
{
	private JCheckBox box;
	
	public BackgroundVisibilityCellRenderer()
	{
		box = new JCheckBox();
		box.setBackground(Color.white);
	}

	@Override
	public Component getListCellRendererComponent(
			JList<? extends Graphic> list, Graphic value,
			int index, 
			boolean isSelected, boolean cellHasFocus) 
	{
		
		box.setSelected(value.isVisible());
		
		return box;
	}
}
