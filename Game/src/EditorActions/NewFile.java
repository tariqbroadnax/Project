package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Utilities.GUIUtils;

public class NewFile extends AbstractAction
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "NewFile16.gif", largeIconFileName = PATH + "NewFile24.gif";

	public NewFile()
	{
		super("NewFile");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "NewFile");
		putValue(LONG_DESCRIPTION, "NewFile");
		
		putValue(NAME, "NewFile");
		putValue(ACTION_COMMAND_KEY, "NewFile");
		putValue(MNEMONIC_KEY, KeyEvent.VK_F);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_F);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}
}
