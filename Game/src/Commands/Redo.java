package Commands;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.undo.UndoManager;

import EditorGUI.GUIResources;

public class Redo extends AbstractAction
{
	private GUIResources resources;
	
	public Redo(GUIResources resources)
	{
		this.resources = resources;
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		UndoManager undoManager =
				resources.getUndoManager();
		
		if(undoManager.canRedo())
			undoManager.redo();
	}
	
	

}
