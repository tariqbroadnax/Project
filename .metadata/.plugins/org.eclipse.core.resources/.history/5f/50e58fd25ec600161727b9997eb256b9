package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import Utilities.GUIUtils;

public class Cut extends AbstractAction
{
	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "Cut16.gif",
							   largeIconFileName =
			PATH + "Cut24.gif";
	
	public Cut()
	{
		super("Cut");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Cut");
		putValue(LONG_DESCRIPTION, "Cut");
		
		putValue(NAME, "Cut");
		putValue(ACTION_COMMAND_KEY, "Cut");
		putValue(MNEMONIC_KEY, KeyEvent.VK_T);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_T);
		
		KeyStroke keyStroke = 
				KeyStroke.getKeyStroke("control X");
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
