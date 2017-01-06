package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Utilities.GUIUtils;

public class ZoomOut extends AbstractAction
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "ZoomOut16.gif", largeIconFileName = PATH + "ZoomOut24.gif";

	public ZoomOut()
	{
		super("ZoomOut");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "ZoomOut");
		putValue(LONG_DESCRIPTION, "ZoomOut");
		
		putValue(NAME, "ZoomOut");
		putValue(ACTION_COMMAND_KEY, "ZoomOut");
		putValue(MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_O);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, smallIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
