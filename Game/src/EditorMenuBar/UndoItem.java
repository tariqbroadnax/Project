package EditorMenuBar;

import javax.swing.JMenuItem;

import EditorActions.Undo;
import EditorGUI.GUIResources;
import EditorGUI.UndoListener;
import EditorGUI.UndoManager;

public class UndoItem extends JMenuItem
	implements UndoListener
{
	public UndoItem(GUIResources resources, Undo undo)
	{
		super("Undo - Ctrl+X");
	
		addActionListener(undo);
		
		UndoManager undoManager = 
				resources.getUndoManager();
		
		setEnabled(undoManager);
		
		undoManager.addUndoListener(this);
	}
	
	private void setEnabled(UndoManager undoManager)
	{
		boolean enabled = undoManager.canUndo();
		
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
