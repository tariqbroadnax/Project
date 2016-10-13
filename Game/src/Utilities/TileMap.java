package Utilities;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;

import DataStructure.Tile;

public class TileMap<E>
	implements Serializable
{
	private ArrayList<Collection<E>> tiles;
	
	private int columns, rows;
	
	private double tileWidth, tileHeight;
	
	private Point2D.Double start;
	
	public TileMap()
	{
		this(10, 10);
	}
	
	public TileMap(int columns, int rows)
	{
		this(columns, rows, 10, 10);
	}
	
	public TileMap(int columns, int rows,
				   double tileWidth, double tileHeight)
	{
		this(columns, rows,
			 tileWidth, tileHeight,
			 new Point2D.Double());
	}
	
	public TileMap(int columns, int rows, 
				   double tileWidth, double tileHeight,
				   Point2D.Double start)
	{
		this.columns = columns;
		this.rows = rows;

		this.tileWidth = tileWidth;
		this.tileHeight = tileHeight;
		
		this.start = start;
		
		tiles = new ArrayList<Collection<E>>(rows * columns);

		instantiateTilesWithList();
	}
	
	public void addToTile(E obj, int column, int row)
	{
		tiles.get(columns * row + column)
			 .add(obj);
	}
	
	public void addToTile(E obj, Tile tile)
	{
		addToTile(obj, tile.col, tile.row);
	}
	
	public void addToTile(E obj, Point2D.Double objLoc)
	{
		Tile tile = tileOf(objLoc);
				
		addToTile(obj, tile.col, tile.row);
	}
	
	public Collection<Tile> tilesOfBound(Rectangle2D.Double bound)
	{
		Collection<Tile> tiles =
				new LinkedList<Tile>();
		
		Point2D.Double tl = new Point2D.Double(bound.x, bound.y),
					   br = new Point2D.Double(bound.x + bound.width,
							   				   bound.y + bound.height);
		
		Tile tlTile = tileOf(tl),
			 brTile = tileOf(br);
		
		
		for(int row = tlTile.row; row <= brTile.row; row++)
			for(int col = tlTile.col; col <= brTile.col; col++)
				tiles.add(new Tile(col, row));
			
		return tiles;
	}
	
	public Collection<E> getCollectionInTile(int column, int row)
	{
		return tiles.get(row * columns + column);
	}
	
	public Collection<E> collectionOfTile(Tile tile)
	{
		return getCollectionInTile(tile.col, tile.row);
	}
	
	public void clear()
	{
		for(Collection<E> tileColl : tiles)
			tileColl.clear();
	}
	
	public double getTileWidth()
	{
		return tileWidth;
	}
	
	public double getTileHeight()
	{
		return tileHeight;
	}
	
	public Point2D.Double getStartLocation()
	{
		return start;
	}
	
	public int getRowCount()
	{
		return rows;
	}
	
	public int getColumnCount()
	{
		return columns;
	}
	
	public Collection<E> objsWithinDistance(Point2D.Double loc, double radius)
	{
		Collection<E> objs = new LinkedList<E>();
		
		for(Tile tile : tilesWithinDistance(loc, radius))
		{
			for(E obj : collectionOfTile(tile))
				objs.add(obj);
		}
				
		return objs;
	}
	
	public Collection<Tile> tilesWithinDistance(Point2D.Double loc, double radius)
	{
		Collection<Tile> tiles = new LinkedList<Tile>();
		
		Tile tile = tileOf(loc);
	
		int leftTileCount = (int) Math.ceil(radius / tileWidth),
			rightTileCount = leftTileCount,
			upTileCount = (int) Math.ceil(radius / tileHeight),
			downTileCount = upTileCount;
	
		int minCol = tile.col - leftTileCount,
			minRow = tile.row - upTileCount,
			maxCol = tile.col + rightTileCount,
			maxRow = tile.row + downTileCount;
		
		
		minCol = minCol >= 0 ? minCol : 0;
		minRow = minRow >= 0 ? minRow : 0;
		maxCol = maxCol < columns ? maxCol : columns - 1;
		maxRow = maxRow < rows ? maxCol : rows - 1;
	
		for(int r = minRow ; r <= maxRow; r++)
			for(int c = minCol; c <= maxCol; c++)
				tiles.add(new Tile(c, r));
		
		return tiles;
	}
		
	public Tile tileOf(Point2D.Double loc)
	{
		return tileOf(tileWidth, tileHeight, start, loc);
	}
	
	public static Tile tileOf(
			double tileWidth, double tileHeight,
			Point2D.Double start, Point2D.Double loc)
	{
		int c = (int)((loc.x - start.x) / tileWidth),
			r = (int)((loc.y - start.y) / tileHeight);
		
		return new Tile(c, r);
	}
	
	public String toString()
	{
		String str = "TileMap - " + 
				  "\nstart: " + start +
				  "\ntile width: " + tileWidth + " tile height: " + tileHeight +
				  "\ncolumns: " + columns + " rows: " + rows;	
		
		for(int r = 0; r < rows; r++)
			for(int c = 0; c < columns; c++)
			{		
				for(E obj : getCollectionInTile(c, r))
					str += "\n" + "[" + c + ", " + r + ", "+ obj.toString() + "]";
			}
		
		return str;
	}
	
	private void instantiateTilesWithList()
	{
		for(int r = 0; r < rows; r++)
			for(int c = 0; c < columns; c++)
				tiles.add(new LinkedList<E>());
	}
}
