package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Utilities.GUIUtils;

public class Zoom extends AbstractAction
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "Zoom16.gif", largeIconFileName = PATH + "Zoom24.gif";

	public Zoom()
	{
		super("Zoom");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Zoom");
		putValue(LONG_DESCRIPTION, "Zoom");
		
		putValue(NAME, "Zoom");
		putValue(ACTION_COMMAND_KEY, "Zoom");
		putValue(MNEMONIC_KEY, KeyEvent.VK_Z);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_Z);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
