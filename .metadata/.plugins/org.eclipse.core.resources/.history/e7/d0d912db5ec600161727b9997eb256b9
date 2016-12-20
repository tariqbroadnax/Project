package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import Utilities.GUIUtils;

public class Paste extends AbstractAction
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "Paste16.gif", largeIconFileName = PATH + "Paste24.gif";

	public Paste()
	{
		super("Paste");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Paste");
		putValue(LONG_DESCRIPTION, "Paste");
		
		putValue(NAME, "Paste");
		putValue(ACTION_COMMAND_KEY, "Paste");
		putValue(MNEMONIC_KEY, KeyEvent.VK_P);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_P);
		
		KeyStroke keyStroke = KeyStroke.getKeyStroke(
				"control V");
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
