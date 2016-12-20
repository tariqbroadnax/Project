package Editor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Editor.EditorResources;
import Utilities.GUIUtils;

public class SaveAs extends AbstractAction
{
	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "SaveAs16.gif",
							   largeIconFileName =
			PATH + "SaveAs24.gif";
	
	private EditorResources resources;
	
	public SaveAs(EditorResources resources)
	{
		super("SaveAs");
	
		this.resources = resources;
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Save As");
		putValue(LONG_DESCRIPTION, "Save As");
		
		putValue(NAME, "Save As");
		putValue(ACTION_COMMAND_KEY, "Save As");
		putValue(MNEMONIC_KEY, KeyEvent.VK_A);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				KeyEvent.VK_A);
		
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		resources.saveAs();
	}
}