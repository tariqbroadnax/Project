package GUI;

import java.awt.Component;
import java.awt.image.BufferedImage;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

import Ability.Ability;
import EntityComponent.AbilityComponent;
import Utilities.ImagePool;

public class AbilityList extends JList<Ability>
{
	public AbilityList(AbilityComponent comp, ImagePool imgPool)
	{
		DefaultListModel<Ability> model = new DefaultListModel<Ability>();
		
		for(Ability ability : comp.getActives())
			model.addElement(ability);
		
		setModel(model);
		
		setCellRenderer(new AbilityCellRenderer(imgPool));
	}
}

class AbilityCellRenderer extends JLabel
	implements ListCellRenderer<Ability>
{
	private ImagePool imgPool;
	
	public AbilityCellRenderer(ImagePool imgPool)
	{
		this.imgPool = imgPool;
	}
	
	@Override
	public Component getListCellRendererComponent(
			JList list, Ability ability,
			int index, 
			boolean selected, boolean hasFocus) 
	{
		setText(ability.getName());
		
		BufferedImage img = imgPool.getOriginal(ability.getIconID());

		setIcon(new ImageIcon(img));
		
		return this;
	}
}
