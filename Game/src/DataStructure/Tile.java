package DataStructure;

public class Tile
{
	public final int col, row;
	
	public Tile(int col, int row)
	{
		this.col = col;
		this.row = row;
	}
	
	public String toString()
	{
		return "[" + col + "," + row + "]";
	}
	
}
