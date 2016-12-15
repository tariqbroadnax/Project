package EditorActions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;
import javax.swing.undo.UndoManager;

import Utilities.GUIUtils;

public class Undo extends AbstractAction
{
	public static final String PATH =
			"jlfgr-1.0\\toolbarButtonGraphics\\general\\";
	
	public static final String smallIconFileName =
			PATH + "Undo16.gif",
							   largeIconFileName =
			PATH + "Undo24.gif";
	
	private UndoManager undoManager;
	
	public Undo()
	{
		super("Undo");
		
		ImageIcon smallIcon = GUIUtils.ImageIcon(
				smallIconFileName),
				  largeIcon = GUIUtils.ImageIcon(
				largeIconFileName);
		
		putValue(SHORT_DESCRIPTION, "Undo");
		putValue(LONG_DESCRIPTION, "Undo");
		
		putValue(NAME, "Undo");
		putValue(ACTION_COMMAND_KEY, "Undo");
		putValue(MNEMONIC_KEY, KeyEvent.VK_U);
		putValue(DISPLAYED_MNEMONIC_INDEX_KEY,
				 KeyEvent.VK_U);
		
		KeyStroke keyStroke = KeyStroke.getKeyStroke(
				"control Z");
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
		if(undoManager.canUndo())
			undoManager.undo();
	}
}
