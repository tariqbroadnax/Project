package Editor.layer_selector;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import Editor.EditorResources;
import Tileset.Tile;
import Tileset.TileMap;

public class RemoveTile implements UndoableEdit
{
	private EditorResources resources;
	
	private TileMap tm;
	
	private Tile prevTile;
	
	private int row, col;
	
	private boolean alive;
	
	public RemoveTile(
			EditorResources resources,
			TileMap tm, int row, int col)
	{
		this.resources = resources;
		this.tm = tm;
		this.row = row;
		this.col = col;
		
		alive = true;
	}
	

	public Tile getPrevTile() {
		return prevTile;
	}
	
	public int getRow() {
		return row;
	}
	
	public int getCol() {
		return col;
	}

	public void invoke()
	{
		prevTile = tm.get(row, col);
		tm.set(row, col, null);
		resources.notifyOfSceneChange();
	}

	@Override
	public void undo() throws CannotUndoException 
	{
		if(!alive)
			throw new CannotUndoException();
		
		tm.set(row, col, prevTile);
		resources.notifyOfSceneChange();
	}

	@Override
	public void redo() throws CannotRedoException 
	{
		if(!alive)
			throw new CannotUndoException();
		
		invoke();
	}

	@Override 
	public boolean replaceEdit(UndoableEdit edit) 
	{	
		if(edit instanceof RemoveTile)
		{
			RemoveTile rtEdit = (RemoveTile)edit;
	
			if(rtEdit.getRow() == row &&
			   rtEdit.getCol() == col)
			{
				rtEdit.die();
				prevTile = rtEdit.getPrevTile();
				return true;
			}
			else
				return false;
		}
		
		return false;
	}


	@Override
	public boolean isSignificant() {
		return true;
	}

	@Override
	public boolean canUndo() {
		return alive;
	}

	@Override
	public boolean canRedo() {
		return alive;
	}

	@Override // not used
	public boolean addEdit(UndoableEdit e) {
		return false;
	}

	@Override
	public void die() 
	{
		alive = false;
	}

	@Override // not used
	public String getPresentationName() {
		return "";
	}

	@Override // not used
	public String getRedoPresentationName() {
		return "";
	}

	@Override // not used
	public String getUndoPresentationName() {
		return "";
	}

}
