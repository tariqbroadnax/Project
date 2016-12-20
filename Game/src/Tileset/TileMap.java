package Tileset;

import java.awt.geom.Rectangle2D;

import Graphic.Graphic;
import Graphic.GraphicsContext;
import Graphic.Sprite;
import Maths.Dimension2D;

import static java.lang.Math.min;

public class TileMap extends Graphic
{
	private Dimension2D.Double tileSize;
	
	private Tile[][] map;
	
	public final int rows, cols;

	public TileMap()
	{
		this(40, 40);
	}
	
	public TileMap(int rows, int cols)
	{
		tileSize = new Dimension2D.Double(10, 10);
		
		loc.x = -200;
		loc.y = -200;
		
		this.rows = rows; this.cols = cols;
		
		map = new Tile[cols][rows];
		
		Tile tile = new Tile("GrassTileSet.png", 0, 0);
		
		for(int row = 0; row < rows; row++)
			for(int col = 0; col < cols; col++)
				map[col][row] = tile;
	}
	
	public TileMap(TileMap tm)
	{
		tileSize = new Dimension2D.Double(
				tm.tileSize.width, tm.tileSize.height);
		
		rows = tm.rows; cols = tm.cols;
		
		map = new Tile[cols][rows];

		for(int row = 0; row < rows; row++)
			for(int col = 0; col < cols; col++)
				map[col][row] = tm.map[col][row];
	}

	@Override
	protected void _paint(GraphicsContext gc) 
	{
		Rectangle2D.Double cameraBound =
				gc.camera.normalViewBound();
		
		int startRow = row(cameraBound.y),
			startCol = col(cameraBound.x),
			endRow = row(cameraBound.y + cameraBound.height) + 1,
			endCol = col(cameraBound.x + cameraBound.width) + 1;
		
		startRow = startRow < 0 ? 0 : startRow;
		startCol = startCol < 0 ? 0 : startCol;
		endRow = endRow > rows ? rows : endRow;
		endCol = endCol > cols ? cols : endCol;
		
	//	System.out.println(x(startCol) + " " + y(startRow));
		Sprite sprite = new Sprite();
		
		sprite.setSize(tileSize.width, tileSize.height);
				
		for(int row = startRow; row < endRow; row++)
		{
			for(int col = startCol; col < endCol; col++)
			{
				Tile tile = map[col][row];
				
				if(tile == null) continue;
				
				double x = loc.x + col * tileSize.width,
					   y = loc.y + row * tileSize.height;
		
				sprite.setTile(tile);
				sprite.setLoc(x + tileSize.width / 2,
							  y + tileSize.height / 2);
				
				sprite.paint(gc);
			}
		}
	}
	
	public void set(int row, int col, Tile tile) {
		map[col][row] = tile;
	}
	
	public void setFrame(int rows, int cols)
	{
		Tile[][] map = new Tile[cols][rows];
		
		for(int row = 0; row < min(rows,this.rows); row++)
			for(int col = 0; col < min(col, this.cols); col++)
				map[col][row] = this.map[col][row];
		
		this.map = map;
	}
	
	public void setTileSize(double twidth, double theight)
	{
		tileSize.width = twidth;
		tileSize.height = theight;
	}
	
	public int row(double y)
	{
		y -= loc.y;
		
		if(y < 0) return -1;
		else
			return (int) Math.floor(y/tileSize.height);
	}
	
	public int col(double x)
	{
		x -= loc.x;
		
		if(x < 0) return -1;
		else
			return (int) Math.floor(x/tileSize.width);	
	}
	
	public double y(int row) {
		return loc.y + row * tileSize.height;
	}
	
	public double x(int col) {
		return loc.x + col * tileSize.width;
	}
	
	public Dimension2D.Double tileSize() 
	{
		return new Dimension2D.Double(
				tileSize.width, tileSize.height);
	}
	
	@Override
	public Rectangle2D.Double getBound() 
	{
		double width = tileSize.width * cols,
			   height = tileSize.height * rows;
		
		return new Rectangle2D.Double(loc.x, loc.y, 
									  width, height);
	}

	@Override
	public Object clone() {
		return new TileMap(this);
	}
}