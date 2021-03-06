package EditorGUI;

import java.util.Collection;
import java.util.LinkedList;

import javax.swing.undo.UndoableEdit;

public class UndoManager extends javax.swing.undo.UndoManager
{
	private Collection<UndoListener> listeners;
	
	public UndoManager()
	{
		listeners = new LinkedList<UndoListener>();
	}
	
	public void addUndoListener(UndoListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeUndoListener(UndoListener listener)
	{
		listeners.remove(listener);
	}
	
	public void undo()
	{
		super.undo();
		
		for(UndoListener listener : listeners)
			listener.undoOccurred(this);
	}
	
	public void redo()
	{
		super.redo();
		
		for(UndoListener listener : listeners)
			listener.redoOccurred(this);
	}

	public boolean addEdit(UndoableEdit anEdit)
	{
		boolean added = super.addEdit(anEdit);
		
		if(added)
			for(UndoListener listener : listeners)
				listener.editAdded(this);
		
		return added;
	}
}