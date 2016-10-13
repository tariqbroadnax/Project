package Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

import EditorGUI.GUIResources;

public class Undo extends AbstractAction
{
	private GUIResources resources;
	
	public Undo(GUIResources resources) {
		this.resources = resources;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		UndoManager undoManager = resources.getUndoManager();
		
		if(undoManager.canUndo())
			resources.getUndoManager()
					 .undo();
	}
}
