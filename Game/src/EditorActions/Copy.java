package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import Utilities.GUIUtils;

public class Copy extends AbstractAction 
{
	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "Copy16.gif",
							   largeIconFileName =
			PATH + "Copy24.gif";
	
	public Copy()
	{
		super("Copy");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Copy");
		putValue(LONG_DESCRIPTION, "Copy");
		
		putValue(NAME, "Copy");
		putValue(ACTION_COMMAND_KEY, "Copy");
		putValue(MNEMONIC_KEY, KeyEvent.VK_C);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_C);
		
		KeyStroke keyStroke = 
				KeyStroke.getKeyStroke("control C");
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
