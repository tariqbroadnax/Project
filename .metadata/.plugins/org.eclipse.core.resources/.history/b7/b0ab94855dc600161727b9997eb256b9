package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

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
	
	private GUIResources resources;
	
	public Save() 
	{
		super("Save");
		
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
	
	public void setResources(
			GUIResources resources)
	{
		this.resources = resources;
	}

	private void save()
	{
		if(resources.canSave())
		{
			try {
				resources.saveScene();
			} catch (IOException e) {
				JOptionPane.showMessageDialog(
						null, e.getMessage());
			}
		}
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		save();
	}
}
