package Editor;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Utilities.GUIUtils;

public class TSZoomIn extends AbstractAction
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "ZoomIn16.gif", largeIconFileName = PATH + "ZoomIn24.gif";

	private final TileSelector selector;
	
	public TSZoomIn(final TileSelector selector)
	{
		super("ZoomIn");
		
		this.selector = selector;
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "ZoomIn");
		putValue(LONG_DESCRIPTION, "ZoomIn");
		
		putValue(NAME, "ZoomIn");
		putValue(ACTION_COMMAND_KEY, "ZoomIn");
		putValue(MNEMONIC_KEY, KeyEvent.VK_I);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_I);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		selector.zoomIn();
	}
}
