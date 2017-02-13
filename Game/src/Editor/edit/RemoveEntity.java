package Editor.edit;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import Editor.EditorResources;
import Entity.Entity;

public class RemoveEntity implements UndoableEdit
{
	private EditorResources resources;
	
	private Entity ent;
	
	private boolean alive;
	
	public RemoveEntity(Entity ent, EditorResources resources)
	{
		this.ent = ent;
		
		this.resources = resources;
		
		alive = true;
	}
	
	public void invoke() {
		resources.scene.removeEntityNow(ent);
		resources.notifyOfSceneChange();
	}
	
	@Override
	public boolean addEdit(UndoableEdit arg0) {
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
		return "remove entity";
	}

	@Override
	public String getRedoPresentationName() {
		return"re-remove entity again";
	}

	@Override
	public String getUndoPresentationName() {
		return "re-add entity";
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
	public void undo() throws CannotUndoException {
		resources.scene.addEntityNow(ent);
		resources.notifyOfSceneChange();
	}

}
