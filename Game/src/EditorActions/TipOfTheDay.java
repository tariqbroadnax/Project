package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Utilities.GUIUtils;

public class TipOfTheDay extends AbstractAction
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "TipOfTheDay16.gif", largeIconFileName = PATH + "TipOfTheDay24.gif";

	public TipOfTheDay()
	{
		super("TipOfTheDay");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "TipOfTheDay");
		putValue(LONG_DESCRIPTION, "TipOfTheDay");
		
		putValue(NAME, "TipOfTheDay");
		putValue(ACTION_COMMAND_KEY, "TipOfTheDay");
		putValue(MNEMONIC_KEY, KeyEvent.VK_T);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_T);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
