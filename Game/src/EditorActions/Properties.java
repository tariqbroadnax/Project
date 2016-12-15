package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Utilities.GUIUtils;

public class Properties extends AbstractAction
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "Properties16.gif", largeIconFileName = PATH + "Properties24.gif";

	public Properties()
	{
		super("Properties");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Properties");
		putValue(LONG_DESCRIPTION, "Properties");
		
		putValue(NAME, "Properties");
		putValue(ACTION_COMMAND_KEY, "Properties");
		putValue(MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_P);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
