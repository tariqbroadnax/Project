package Editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import Editor.EditorResources;
import Editor.TileMapDialog;

public class InsertTileMap extends AbstractAction
{	
	private TileMapDialog dialog;
	
	public InsertTileMap(EditorResources resources)
	{
		super("Tile Map");
		
		dialog = new TileMapDialog(resources);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		dialog.reset();
		dialog.setVisible(true);
	}
}
