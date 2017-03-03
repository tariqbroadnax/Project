package Editor.tile;

import java.util.List;

import javax.swing.AbstractListModel;

import Editor.Editor;
import Tileset.Tileset;

public class TSModel extends AbstractListModel<Tileset>
{
	public void addTileset(Tileset ts)
	{
		List<Tileset> tss = 		
				Editor.RESOURCES.getEditorAssets()
					  .getTilesets();

		int size = tss.size();
	
		tss.add(ts);
		
		fireIntervalAdded(this, size, size+1);	
	}
	
	public void removeTileset(Tileset ts)
	{
		
	}
	
	@Override
	public Tileset getElementAt(int index) 
	{
		return Editor.RESOURCES.getEditorAssets()
		   					   .getTilesets()
		   					   .get(index);
	}

	@Override
	public int getSize() 
	{
		return Editor.RESOURCES.getEditorAssets()
							   .getTilesets()
							   .size();
			
	}
}
