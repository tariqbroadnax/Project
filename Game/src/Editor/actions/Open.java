package Editor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import Editor.EditorResources;
import Utilities.GUIUtils;

public class Open extends AbstractAction{

	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "Open16.gif",
							   largeIconFileName =
			PATH + "Open24.gif";
		
	private EditorResources resources;
	
	public Open(EditorResources resources) 
	{
		super("Open");
		
		this.resources = resources;
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Open");
		putValue(LONG_DESCRIPTION, "Open");
		
		putValue(NAME, "Open");
		putValue(ACTION_COMMAND_KEY, "Open");
		putValue(MNEMONIC_KEY, KeyEvent.VK_O);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				 KeyEvent.VK_O);
		
		KeyStroke keyStroke = KeyStroke.getKeyStroke(
				"control O");
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, smallIcon);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		resources.open();
	}

}
