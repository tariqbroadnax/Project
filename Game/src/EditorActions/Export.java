package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import Utilities.GUIUtils;

public class Export extends AbstractAction
{
	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "Export16.gif",
							   largeIconFileName =
			PATH + "Export24.gif";
	
	public Export()
	{
		super("Export");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Export");
		putValue(LONG_DESCRIPTION, "Export");
		
		putValue(NAME, "Export");
		putValue(ACTION_COMMAND_KEY, "Export");
		putValue(MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_O);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
