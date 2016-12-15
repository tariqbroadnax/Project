package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import Utilities.GUIUtils;

public class SaveAll extends AbstractAction
{
	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "SaveAll16.gif",
							   largeIconFileName =
			PATH + "SaveAll24.gif";
	
	public SaveAll()
	{
		super("Refresh");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Save All");
		putValue(LONG_DESCRIPTION, "Save All");
		
		putValue(NAME, "Save All");
		putValue(ACTION_COMMAND_KEY, "Save All");
		putValue(MNEMONIC_KEY, KeyEvent.VK_E);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_F);
		
		KeyStroke keyStroke = 
				KeyStroke.getKeyStroke(
						"control shift S");
		
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

