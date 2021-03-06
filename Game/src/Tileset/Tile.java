package Tileset;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;

import Graphic.ImagePool;

public class Tile implements Serializable
{	
	public final Tileset src;

	public final int row, col;
	
	private Rectangle bound;
	
	public Tile(Tileset src, int row, int col)
	{
		this.src = src;
		this.row = row; this.col = col;
		
		//ImagePool.instance.request(src.getFile());

		initBound();
	}
	
	public void initBound()
	{
		File file = src.getFile();
		
		BufferedImage img = ImagePool.instance.getImage(file);
		
		int rows = src.getRows(),
			cols = src.getCols();
		
		int w = img.getWidth() / cols,
			h = img.getHeight() / rows,
			x = img.getWidth() * col / cols,
			y = img.getHeight() * row / rows;
		
		bound = new Rectangle(x, y, w, h);
	}
	
	public Rectangle getBound()
	{
		return bound;
	}
	
	@Override
	public boolean equals(Object o)
	{
		if(o instanceof Tile)
		{
			Tile tile = (Tile)o;
			
			String path = src.getFile()
						     .getAbsolutePath(),
				   path2 = tile.src.getFile()
				   				   .getAbsolutePath();
			
			
			return path.equals(path2) &&
				   row == tile.row && col == tile.col;
		}
		
		return false;
	}
	
	@Override
	public int hashCode()
	{
		return src.getFile()
				  .getAbsolutePath()
				  .hashCode();
	}
	
	@Override
	public String toString()
	{
		return "[" + src.getFile().getName() + ", r = " + row + ", col = " + col + "]";
	}

}
