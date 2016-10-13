package GUI;

import javax.swing.JInternalFrame;
import javax.swing.JScrollPane;

import EntityComponent.AbilityComponent;
import Utilities.ImagePool;

public class AbilityFrame extends JInternalFrame
{
	private JScrollPane scrollPane;
	
	private AbilityList list;
	
	public AbilityFrame(AbilityComponent comp, ImagePool imgPool)
	{
		super("Abilities", false, true);
		
		list = new AbilityList(comp, imgPool);
		
		scrollPane = new JScrollPane(list);	
		
		add(scrollPane);
	}
	
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		
		list.setPreferredSize(scrollPane.getViewport().getSize());
	}
}
