package Editor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JToggleButton;

import Editor.EditorResources;
import Editor.ResourceListener;
import Utilities.GUIUtils;

public class ToggleTiledModeButton extends JToggleButton 
	implements ActionListener, ResourceListener
{
	private EditorResources resources;
		
	public ToggleTiledModeButton(
			EditorResources resources)
	{
		this.resources = resources;
		
		ImageIcon icon = GUIUtils.ImageIcon(
				"tiled.png");

		setIcon(icon);
		
		resources.addResourceListener(this);
		
		boolean tiledMode = resources.tiledMode();
		setSelected(tiledMode);

	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		boolean tiledMode = resources.tiledMode();
		
		resources.setTiledMode(!tiledMode);
	}
	
	@Override
	public void tiledModeChanged(boolean tiledMode)
	{	
		setSelected(tiledMode);
	}
}
