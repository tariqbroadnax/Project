package Editor.tile;

import javax.swing.JToggleButton;
import javax.swing.JToolBar;

public class TSToolbar extends JToolBar
{
	public TSToolbar(final TileSelector selector)
	{
		TSZoomIn zoomIn = new TSZoomIn(selector);
		TSZoomOut zoomOut = new TSZoomOut(selector);
		TSToggleGrid toggleGrid = new TSToggleGrid(selector);
		
		JToggleButton toggleButt =
				 new JToggleButton(toggleGrid);
		toggleButt.setSelected(true);
		
		add(zoomIn);
		add(zoomOut);
		add(toggleButt);
		
	}
}
