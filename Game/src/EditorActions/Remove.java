package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Utilities.GUIUtils;

public class Remove extends AbstractAction
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "Remove16.gif", largeIconFileName = PATH + "Remove24.gif";

	public Remove()
	{
		super("Remove");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Remove");
		putValue(LONG_DESCRIPTION, "Remove");
		
		putValue(NAME, "Remove");
		putValue(ACTION_COMMAND_KEY, "Remove");
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_R);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
