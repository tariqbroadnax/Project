package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import Utilities.GUIUtils;

public class Delete extends AbstractAction
{
	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "Delete16.gif",
							   largeIconFileName =
			PATH + "Delete24.gif";
	
	public Delete()
	{
		super("Delete");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Delete");
		putValue(LONG_DESCRIPTION, "Delete");
		
		putValue(NAME, "Delete");
		putValue(ACTION_COMMAND_KEY, "Delete");
		putValue(MNEMONIC_KEY, KeyEvent.VK_D);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_D);
		
		KeyStroke keyStroke = 
			    KeyStroke.getKeyStroke(
			    		KeyEvent.VK_DELETE, 0);
		
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
