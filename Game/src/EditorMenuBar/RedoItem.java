package EditorMenuBar;

import javax.swing.JMenuItem;

import EditorGUI.GUIResources;
import EditorGUI.UndoListener;
import EditorGUI.UndoManager;

public class RedoItem extends JMenuItem
	implements UndoListener
{
	public RedoItem(GUIResources resources)
	{
		super("Redo - Ctrl+Y");
		
		UndoManager undoManager =
				resources.getUndoManager();
		
		setEnabled(undoManager);
		
		undoManager.addUndoListener(this);

	}
	
	private void setEnabled(UndoManager undoManager)
	{
		boolean enabled = undoManager.canRedo();
		
		setEnabled(enabled);		
	}

	@Override
	public void undoOccurred(UndoManager src) {
		setEnabled(src);
	}

	@Override
	public void redoOccurred(UndoManager src) {
		setEnabled(src);
	}

	@Override
	public void editAdded(UndoManager src) {
		setEnabled(src);
	}
}
