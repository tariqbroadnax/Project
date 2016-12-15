package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import Utilities.GUIUtils;

public class Refresh extends AbstractAction
{
	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "Refresh16.gif",
							   largeIconFileName =
			PATH + "Refresh24.gif";
	
	public Refresh()
	{
		super("Refresh");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION,
				"Refreshes the package explorer");
		putValue(LONG_DESCRIPTION,
				"Refreshes the package explorer, "
				+ "deleting removed nodes, and adding"
				+ "new nodes");
		
		putValue(NAME, "Refresh");
		putValue(ACTION_COMMAND_KEY, "Refresh");
		putValue(MNEMONIC_KEY, KeyEvent.VK_F);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_F);
		
		KeyStroke keyStroke = KeyStroke.getKeyStroke("F5");
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);		
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
