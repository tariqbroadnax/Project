package GUI;

import java.awt.Component;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import Game.GameResources;

public class DebugMenu extends JPanel
{
	public DebugMenu(GameResources resources)
	{
		BoxLayout layout = 
				new BoxLayout(this, BoxLayout.Y_AXIS);
		
		setLayout(layout);
		setOpaque(false);
		setPreferredSize(new Dimension(200, 800));
	}
	
	public void requestFocus()
	{
		if(getComponentCount() == 0)
			return;
		else
			getComponent(0)
			.requestFocus();
	}
	
	public void add(MenuButton button)
	{
		button.setMaximumSize(new Dimension(200, 25));
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		super.add(button);
	}
	
	public int indexOf(MenuButton button)
	{
		for(int i = 0; i < getComponentCount(); i++)
			if(getComponent(i) == button)
				return i;
		return -1;
	}
}
