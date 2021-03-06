package Tileset;

import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Graphic.GraphicsContext;
import Graphic.Sprite;
import Maths.Dimension2D;

public class TileMap implements Serializable
{
	private Point2D.Double loc;
	
	private Dimension2D.Double tileSize;
	
	private Tile[][] tiles;
	
	public TileMap()
	{
		loc = new Point2D.Double();
		
		tileSize = new Dimension2D.Double(10, 10);
				
		tiles = new Tile[250][250];
		
		loc.x = loc.y = -1250; 

		Tileset ts = new Tileset("gts.png", 1, 2);
		
		for(int row = 0; row < 250; row++)
			for(int col = 0; col < 250; col++)
			{
				set(row, col, ts.get(0, 0));
			}
	}
	
	public TileMap(TileMap tm)
	{
		loc = (Point2D.Double) tm.loc.clone();
		
		tileSize = (Dimension2D.Double) tm.tileSize.clone();
		
		int rows = tm.rows(), 
			cols = tm.cols();
		
		tiles = new Tile[cols][rows];

		for(int row = 0; row < rows; row++)
			for(int col = 0; col < cols; col++)
				tiles[col][row] = tm.tiles[col][row];
	}
	
	public void paint(GraphicsContext gc)
	{
		Rectangle2D.Double normScrBound = gc.camera.normalScreenBound();
		
		int startCol = col(normScrBound.x),
			startRow = row(normScrBound.y),
			endCol = col(normScrBound.x + normScrBound.width) + 1,
			endRow = row(normScrBound.y + normScrBound.height) + 1;
		
		int rows = rows(), cols = cols();
		
		startCol = startCol < 0 ? 0 : 
				   startCol > cols ? cols - 1 : startCol;
		endCol = endCol < 0 ? 1 :
				 endCol >= cols ? cols : endCol;
		startRow = startRow < 0 ? 0 :
				   startRow > rows ? rows - 1 : startRow;
		endRow = endRow < 0 ? 1 : 
				 endRow > rows ? rows : endRow;
				   
		Sprite sprite = new Sprite();
		
		sprite.setSize(tileSize.width, tileSize.height);
		
		for(int row = startRow; row < endRow; row++)
		{
			for(int col = startCol; col < endCol; col++)
			{	
				Tile tile = tiles[col][row];
				
				if(tile != null)
				{
					double x = x(col) + tileSize.width/2,
						   y = y(row) + tileSize.height/2;
										
					sprite.setLoc(x, y);
					sprite.setTile(tile);
					
					sprite.paint(gc);					
				}
			}
		}
	}
	
	public int rows() {
		return tiles[0].length;
	}
	
	public int cols() {
		return tiles.length;
	}

	public void set(int row, int col, Tile tile) 
	{
		int rows = rows(), cols = cols();
	
		tiles[col][row] = tile;
	}
	
	public Tile get(int row, int col) 
	{
		if(-1 < row && row < rows() &&
		   -1 < col && col < cols())
			return tiles[col][row];
		else
			return null;
	}
	
	public void setTileSize(double twidth, double theight)
	{
		tileSize.width = twidth;
		tileSize.height = theight;
	}
	
	public int row(double y)
	{
		y -= loc.y;
		
		int row = (int) Math.floor(y/tileSize.height);
		
		if(y < 0) row -= 1;
		
		return row;
	}
	
	public int col(double x)
	{
		x -= loc.x;
		
		int col = (int) Math.floor(x/tileSize.width);	
		
		if(x < 0) col -= 1;

		return col;
	}
	
	public double y(int row) {
		return loc.y + row * tileSize.height;
	}
	
	public double x(int col) {
		return loc.x + col * tileSize.width;
	}
	
	private Point2D.Double tileLoc(int row, int col) {
		return new Point2D.Double(x(col), y(row));
	}

	public TMCell cellAtLoc(Double loc) {
		return new TMCell(this, row(loc.y), col(loc.x));
	}
	
	public List<TMCell> getRowCells(int row) 
	{
		int cols = cols();
		
		ArrayList<TMCell> cells = new ArrayList<TMCell>(cols);
		
		for(int col = 0; col < cols; col++)
		{
			TMCell cell = new TMCell(this, row, col);
			
			cells.add(cell);
		}
		
		return cells;
	}
	
	public List<TMCell> getColCells(int col) 
	{
		int rows = rows();
		
		ArrayList<TMCell> cells = new ArrayList<TMCell>(rows);
		
		for(int row = 0; row < rows; row++)
		{
			TMCell cell = new TMCell(this, row, col);
			
			cells.add(cell);
		}
		
		return cells;
	}
	
	public List<TMCell> getCells() 
	{
		int rows = rows(), cols = cols();
		
		ArrayList<TMCell> cells = new ArrayList<TMCell>(rows * cols);

		for(int row = 0; row < rows; row++)
			for(int col = 0; col < cols; col++)
			{
				TMCell cell = new TMCell(this, row, col);
				
				cells.add(cell);
			}
		
		return cells;
	}
	
	public Dimension2D.Double tileSize() 
	{
		return new Dimension2D.Double(
				tileSize.width, tileSize.height);
	}

	@Override
	public Object clone() {
		return new TileMap(this);
	}

}