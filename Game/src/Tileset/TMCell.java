package Tileset;

public class TMCell 
{
	public final TileMap src;
	
	public final int row, col;
	
	public TMCell(TileMap src, int row, int col)
	{
		this.src = src;
		
		this.row = row;	this.col = col;
	}
	
	public void set(Tile tile) {
		src.set(row, col, tile);
	}
	
	public Tile get() {
		return src.get(row, col);
	}
	
	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof TMCell)
		{
			TMCell cell = (TMCell)obj;
			
			return src == cell.src &&
				   row == cell.row &&
				   col == cell.col;
		}
		
		return false;
	}
}
