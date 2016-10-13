package Commands;

import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;

import javax.swing.Action;
import javax.swing.undo.UndoableEdit;

import EditorGUI.GUIResources;

public abstract class Command 
	implements Action, UndoableEdit
{
	protected GUIResources resources;
	
	public Command(GUIResources resources)
	{
		this.resources = resources;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		_do();	
		
		resources.getUndoManager()
				 .addEdit(this);	
	}

	public abstract void _do();
	
	@Override
	public void redo()  
	{
		_do();
	}

	@Override
	public abstract void undo();
	
	@Override
	public boolean addEdit(UndoableEdit e) {
		// NOT used
		return false;
	}

	@Override
	public boolean canRedo() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return true;
	}

	@Override
	public void die() {
		// not used
	}

	@Override
	public String getPresentationName() {
		// not used
		return toString();
	}

	@Override
	public String getRedoPresentationName() {
		return toString();
	}

	@Override
	public String getUndoPresentationName() {
		return toString();
	}

	@Override
	public boolean isSignificant() {
		return true;
	}

	@Override
	public boolean replaceEdit(UndoableEdit e) {
		return false;
	}

	@Override
	public void addPropertyChangeListener(
			PropertyChangeListener e) {}

	@Override
	public Object getValue(String str) {
		return null;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public void putValue(String e, Object arg1) {}

	@Override
	public void removePropertyChangeListener(
			PropertyChangeListener arg0) {}

	@Override
	public void setEnabled(boolean arg0) {}

}
