package Editor;

import java.io.File;

import Tileset.Tileset;

public class TilesetChooser 
{
	private TilesetDialog dialog;
	
	private Tileset tileset;
	
	public TilesetChooser()
	{
		dialog = new TilesetDialog();
	}
	
	public ReturnOption showOpenDialog()
	{
		dialog.setVisible(true);
						
		if(dialog.validSource())
		{
			File file = dialog.file();
			
			int rows = dialog.rows(),
				cols = dialog.cols();
			
			tileset = new Tileset(file, rows, cols);
		
			return ReturnOption.APPROVE;
		}
		else
			return ReturnOption.CANCEL;
	}
	
	public Tileset getSelectedTileset()
	{
		return tileset;
	}
}
