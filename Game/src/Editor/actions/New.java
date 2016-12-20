package Editor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Editor.EditorResources;
import Utilities.GUIUtils;

public class New extends AbstractAction
{
	public static final String PATH = "jlfgr-1.0\\toolbarButtonGraphics\\general\\";

	public static final String smallIconFileName = PATH + "NewFile16.gif", largeIconFileName = PATH + "NewFile24.gif";

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
		
		putValue(NAME, "New");
		putValue(ACTION_COMMAND_KEY, "New");
		putValue(MNEMONIC_KEY, KeyEvent.VK_F);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_F);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		resources._new();
	}
}