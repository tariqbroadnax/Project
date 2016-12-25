package Editor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;

import Utilities.GUIUtils;

public class Redo extends AbstractAction
{	
	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "Redo16.gif",
							   largeIconFileName =
			PATH + "Redo24.gif";
	
	private UndoManager undoManager;
	
	public Redo()
	{
		super("Redo");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Redo");
		putValue(LONG_DESCRIPTION, "Redo");
		
		putValue(NAME, "Redo");
		putValue(ACTION_COMMAND_KEY, "Redo");
		putValue(MNEMONIC_KEY, KeyEvent.VK_R);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				 KeyEvent.VK_R);
		
		KeyStroke keyStroke = KeyStroke.getKeyStroke(
				"control Y");
		putValue(ACCELERATOR_KEY, keyStroke);
		putValue(SMALL_ICON, smallIcon);
		putValue(LARGE_ICON_KEY, largeIcon);	
	}
	
	public void setUndoManager(
			UndoManager undoManager)
	{
		this.undoManager = undoManager;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(undoManager.canRedo())
			undoManager.redo();
	}
	
	

}
