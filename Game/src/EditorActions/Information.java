package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Utilities.GUIUtils;

public class Information extends AbstractAction
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "Information16.gif", largeIconFileName = PATH + "Information24.gif";

	public Information()
	{
		super("Information");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Information");
		putValue(LONG_DESCRIPTION, "Information");
		
		putValue(NAME, "Information");
		putValue(ACTION_COMMAND_KEY, "Information");
		putValue(MNEMONIC_KEY, KeyEvent.VK_I);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_I);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
