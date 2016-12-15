package Editor;

import java.io.Serializable;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import Tileset.Tileset;

public class EditorState 
	implements Serializable
{
	private Set<Tileset> openTilesets;
	
	public EditorState()
	{
		openTilesets = new HashSet<Tileset>();
	}
	
	public void notifyOfOpenTileset(Tileset ts) {
		openTilesets.add(ts);
	}
	
	public void notifyOfClosedTileset(Tileset ts) {
		openTilesets.remove(ts);
	}
	
	public List<Tileset> getOpenTilesets() {
		return new LinkedList<Tileset>(openTilesets);
	}
}