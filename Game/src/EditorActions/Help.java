package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import Utilities.GUIUtils;

public class Help extends AbstractAction 
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "Help16.gif", largeIconFileName = PATH + "Help24.gif";

	public Help()
	{
		super("Help");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Help");
		putValue(LONG_DESCRIPTION, "Help");
		
		putValue(NAME, "Help");
		putValue(ACTION_COMMAND_KEY, "Help");
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
