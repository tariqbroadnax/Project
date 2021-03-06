package Editor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import Editor.EditorResources;
import Utilities.GUIUtils;

public class New extends AbstractAction
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "New16.gif", largeIconFileName = PATH + "New24.gif";

	private EditorResources resources;
	
	public New(EditorResources resources)
	{
		super("New");
		
		this.resources = resources;
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "New");
		putValue(LONG_DESCRIPTION, "New");
		
		KeyStroke keyStroke = KeyStroke.getKeyStroke(
				"control N");
		putValue(ACCELERATOR_KEY, keyStroke);
		
		putValue(NAME, "New");
		putValue(ACTION_COMMAND_KEY, "New");
		putValue(MNEMONIC_KEY, KeyEvent.VK_F);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_F);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, smallIcon);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		resources._new();
	}
}
