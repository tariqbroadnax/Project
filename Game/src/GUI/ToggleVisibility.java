package GUI;

import java.awt.Component;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

public class ToggleVisibility extends AbstractAction
{
	private Component comp;
	
	public ToggleVisibility(Component comp)
	{
		this.comp = comp;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		comp.setVisible(!comp.isVisible());
	}
}
