package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Utilities.GUIUtils;

public class Replace extends AbstractAction
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "Replace16.gif", largeIconFileName = PATH + "Replace24.gif";

	public Replace()
	{
		super("Replace");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Replace");
		putValue(LONG_DESCRIPTION, "Replace");
		
		putValue(NAME, "Replace");
		putValue(ACTION_COMMAND_KEY, "Replace");
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
