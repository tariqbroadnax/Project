package Editor.actions;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;
import javax.swing.KeyStroke;

import EditorGUI.UndoListener;
import EditorGUI.UndoManager;
import Utilities.GUIUtils;

public class Redo extends AbstractAction
	implements UndoListener
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
		
		setEnabled(false);
	}
	
	public void setUndoManager(
			UndoManager undoManager)
	{
		if(this.undoManager != null)
			this.undoManager.removeUndoListener(this);
		
		this.undoManager = undoManager;
		
		if(undoManager == null)
			setEnabled(false);
		else
		{
			checkAndEnable();	
			undoManager.addUndoListener(this);
		}
	}
	
	private void checkAndEnable()
	{
		if(undoManager.canRedo())
			setEnabled(true);
		else
			setEnabled(false);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(undoManager.canRedo())
			undoManager.redo();
	}

	@Override
	public void undoOccurred(UndoManager src) {
		checkAndEnable();
	}

	@Override
	public void redoOccurred(UndoManager src) {
		checkAndEnable();
	}

	@Override
	public void editAdded(UndoManager src) {
		checkAndEnable();
	}
}