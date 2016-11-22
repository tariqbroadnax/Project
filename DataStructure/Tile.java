package DataStructure;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

import Utilities.ImagePool;

public class Tile
{
	public final String fileName;
	
	public final int row, col;
	
	public Tile(String fileName, int row, int col)
	{
		this.fileName = fileName;
		this.row = row;
		this.col = col;
	}
	
	public void paint(Graphics2D g2d, ImagePool pool,
			double x, double y,
			double width, double height)
	{
		AffineTransform origTrans = g2d.getTransform();
		
		BufferedImage img = pool.getTile(fileName, row, col);
		
		if(img == null) return;
		
		int imgW = img.getWidth(), imgH = img.getHeight();
		
		g2d.translate(x, y);
		g2d.scale(width / imgW, height / imgH);
		
		g2d.drawImage(img, 0, 0, null);
		
		g2d.setTransform(origTrans);
	}
	
	public boolean equals(Object obj) 
	{
		return obj instanceof Tile &&
			   equals((Tile) obj);
	}
	
	public boolean equals(Tile tile)
	{
		return fileName == tile.fileName &&
			   row == tile.row &&
			   col == tile.col;
	}
	
	public String toString()
	{
		return "Tile [fileName: " + fileName + 
			   " row: " + row + " col: " + col + "]";
	}
}
