package Tileset;

import java.io.File;
import java.io.Serializable;

public class Tile 
	implements Serializable
{
	public final File file;

	public final int row, col;
	
	public Tile(File file, int row, int col)
	{
		this.file = file;
		this.row = row; this.col = col;
	}
	
	public Tile(String fileName, int row, int col)
	{
		this(new File(fileName), row, col);
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Tile)
		{
			Tile tile = (Tile)o;
			
			return file.getAbsolutePath().equals(tile.file.getAbsolutePath()) &&
				   row == tile.row && col == tile.col;
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return file.getAbsolutePath().hashCode();
	}
	
	@Override
	public String toString()
	{
		return "[" + file.getName() + ", r = " + row + ", col = " + col + "]";
	}
}
