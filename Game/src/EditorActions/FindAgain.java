package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import Utilities.GUIUtils;

public class FindAgain extends AbstractAction {
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "FindAgain16.gif",
			largeIconFileName = PATH + "FindAgain24.gif";

	public FindAgain() {
		super("FindAgain");

		ImageIcon smallIcon = GUIUtils.ImageIcon(smallIconFileName), largeIcon = GUIUtils.ImageIcon(largeIconFileName);

		putValue(SHORT_DESCRIPTION, "Find Again");
		putValue(LONG_DESCRIPTION, "Find Again");

		putValue(NAME, "Find Again");
		putValue(ACTION_COMMAND_KEY, "Find Again");
		putValue(MNEMONIC_KEY, KeyEvent.VK_N);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY, KeyEvent.VK_N);

		KeyStroke keyStroke = KeyStroke.getKeyStroke("Ctrl+Shift+F");
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
