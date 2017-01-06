package Editor.tile;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Utilities.GUIUtils;

public class TSToggleGrid extends AbstractAction
{
	private final TileSelector selector;
	
	public TSToggleGrid(final TileSelector selector)
	{		
		this.selector = selector;
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				"grid.png"),
				  largeIcon = GUIUtils.ImageIcon(
				"grid.png");
		
		putValue(SHORT_DESCRIPTION, "toggle grid lines");
		putValue(LONG_DESCRIPTION, "toggle grid lines");
		
		//putValue(NAME, "ZoomOut");
		putValue(ACTION_COMMAND_KEY, "ZoomOut");
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, smallIcon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		selector.toggleGridLines();
	}
}
