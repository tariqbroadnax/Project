package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import Utilities.GUIUtils;

public class Find extends AbstractAction
{
	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "Find16.gif",
							   largeIconFileName =
			PATH + "Find24.gif";
	
	public Find()
	{
		super("Find");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Find");
		putValue(LONG_DESCRIPTION, "Find");
		
		putValue(NAME, "Find");
		putValue(ACTION_COMMAND_KEY, "Find");
		putValue(MNEMONIC_KEY, KeyEvent.VK_F);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_F);
		
		KeyStroke keyStroke = 
				KeyStroke.getKeyStroke("Ctrl+F");
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
