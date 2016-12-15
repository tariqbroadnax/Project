package Tileset;

import java.awt.Graphics2D;
import java.awt.Rectangle;

import Utilities.ImagePool;

public class SoftTile 
{
	public final Tileset src;
	
	public final Rectangle bound;
	
	public SoftTile(Tileset src, Rectangle bound)
	{
		this.src = src;
		this.bound = bound;
	}
	
	public void paint(Graphics2D g2d, ImagePool pool,
			double x, double y,
			double width, double height)
	{
		
	}
	
	
}
