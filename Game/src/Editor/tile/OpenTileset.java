package Editor.tile;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.JList;

import Tileset.Tileset;

public class OpenTileset extends AbstractAction
{	
	private OpenTilesetDialog dialog;
	
	public OpenTileset(JList<Tileset> list)
	{
		super("Open");
	
		dialog = new OpenTilesetDialog(list);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{	
		dialog.setVisible(true);
	}
}
