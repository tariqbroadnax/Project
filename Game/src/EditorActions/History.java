package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Utilities.GUIUtils;

public class History extends AbstractAction
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "History16.gif", largeIconFileName = PATH + "History24.gif";

	public History()
	{
		super("History");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "History");
		putValue(LONG_DESCRIPTION, "History");
		
		putValue(NAME, "History");
		putValue(ACTION_COMMAND_KEY, "History");
		putValue(MNEMONIC_KEY, KeyEvent.VK_H);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_H);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
