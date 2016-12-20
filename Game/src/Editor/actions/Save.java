package Editor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import Editor.EditorResources;
import EditorGUI.GUIResources;
import Utilities.GUIUtils;

public class Save extends AbstractAction 
{
	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "Save16.gif",
							   largeIconFileName =
			PATH + "Save24.gif";
	
	private EditorResources resources;
	
	public Save(EditorResources resources) 
	{
		super("Save");
		
		this.resources = resources;
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Save");
		putValue(LONG_DESCRIPTION, "Save");
		
		putValue(NAME, "Save");
		putValue(ACTION_COMMAND_KEY, "Save");
		putValue(MNEMONIC_KEY, KeyEvent.VK_S);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				 KeyEvent.VK_S);
		
		KeyStroke keyStroke = KeyStroke.getKeyStroke(
				"control S");
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		resources.save();
	}
}
