package Editor.edit;

import java.util.List;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import Editor.EditorResources;
import Entity.Entity;

public class AddEntities implements UndoableEdit
{
	private EditorResources resources;
	
	private List<Entity> ents;
	
	private boolean alive;
	
	public AddEntities(List<Entity> ents, EditorResources resources)
	{
		this.ents = ents;
		
		this.resources = resources;
		
		alive = true;
	}
	
	public void invoke() 
	{
		for(Entity ent : ents)
			resources.scene.addEntityNow(ent);
		
		resources.notifyOfSceneChange();
	}
	
	@Override
	public boolean addEdit(UndoableEdit e) {
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
		alive = false;
	}

	@Override
	public String getPresentationName() {
		return "add entity";
	}

	@Override
	public String getRedoPresentationName() {
		return "Re-add entity";
	}

	@Override
	public String getUndoPresentationName() {
		return "Remove entity";
	}

	@Override
	public boolean isSignificant() {
		return true;
	}

	@Override
	public void redo() throws CannotRedoException {
		invoke();
	}

	@Override
	public boolean replaceEdit(UndoableEdit arg0) {
		return false;
	}

	@Override
	public void undo() throws CannotUndoException 
	{
		for(Entity ent : ents)
			resources.scene.removeEntityNow(ent);
		resources.notifyOfSceneChange();
	}
}
