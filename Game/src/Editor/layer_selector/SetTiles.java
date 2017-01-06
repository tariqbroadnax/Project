package Editor.layer_selector;

import java.util.HashMap;
import java.util.Map;

import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoableEdit;

import Editor.EditorResources;
import Editor.selection.SelectionHandler;
import Tileset.TMCell;
import Tileset.Tile;
import Tileset.TileMap;

public class SetTiles implements UndoableEdit
{
	private EditorResources resources;
	
	private TileMap tm;
	
	private Tile tile, prevTile;
	
	private boolean alive;
	
	private Map<TMCell, Tile> map;
	
	public SetTiles(EditorResources resources, 
					Tile tile)
	{
		this.resources = resources;
		this.tm = resources.scene.getTileMap();
		this.tile = tile;
	
		map = new HashMap<TMCell, Tile>();
		
		alive = true;
		
		SelectionHandler handler = resources.getSelectionHandler();
		
		for(Object obj : handler.getSelection())
		{
			TMCell cell = (TMCell)obj;
			Tile cellTile = cell.get();
			
			map.put(cell, cellTile);
		}
	}
	
	public Tile getTile() {
		return tile;
	}
	
	public Tile getPrevTile() {
		return prevTile;
	}

	public void invoke()
	{
		resources.notifyOfSceneChange();
	
		for(TMCell cell : map.keySet())
			cell.set(tile);
	}

	@Override
	public void undo() throws CannotUndoException 
	{
		if(!alive)
			throw new CannotUndoException();
		
		for(TMCell cell : map.keySet())
		{
			Tile cellTile = map.get(cell);
			cell.set(cellTile);
		}
		
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
		if(edit instanceof SetTiles)
		{
			SetTiles stEdit = (SetTiles)edit;
	
			if(stEdit.getTile().equals(tile) &&
			   stEdit.map.keySet().equals(map.keySet()))
			{
				stEdit.die();
				
				for(TMCell cell : map.keySet())
				{
					Tile prevTile = stEdit.map.get(cell);
					
					map.put(cell, prevTile);
				}
				
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
		map.clear();
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
