package Graphic;

import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import Maths.Dimension2D;
import Tileset.Tile;

public class Sprite extends Graphic
{
	private String fileName;
	
	private Dimension2D.Double size;
	
	private int row, col;
	
	public Sprite()
	{
		this((String)null);
	}
	
	public Sprite(String fileName)
	{
		this(fileName, -1, -1, 10, 10);
	}
	
	public Sprite(String fileName, int row, int col)
	{
		this(fileName, row, col, 10, 10);
	}
	
	public Sprite(
			String fileName, 
			double width, double height)
	{
		this(fileName, -1, -1, width, height);
	}
	
	public Sprite(
			String fileName, int row, int col,
			double width, double height)
	{
		this.fileName = fileName;
		
		this.row = row;
		this.col = col;
		
		size = new Dimension2D.Double(width, height);
	}
	
	public Sprite(Sprite spr)
	{
		this(spr.fileName, spr.row, spr.col,
			 spr.size.width, spr.size.height);
	}
	
	@Override
	protected void _paint(GraphicsContext gc) 
	{		
		BufferedImage img;
				
		if(row == -1)
			img = gc.getImagePool()
					.getImage(fileName);
		else
			img = gc.getImagePool()
			 		.getImage(fileName, row, col);
				
		Point2D.Double screenLoc =
				gc.camera.screenLocation(loc);
		
		Dimension2D.Double screenSize =
				gc.camera.sizeOnScreen(size);
		
		AffineTransform origTrans = gc.g2d.getTransform();
		
		int imgW = img.getWidth(), imgH = img.getHeight();
		
		gc.g2d.translate(screenLoc.x - screenSize.width/2,
						 screenLoc.y - screenSize.height/2);
		
		gc.g2d.scale(screenSize.width / imgW,
					 screenSize.height / imgH);

		gc.g2d.drawImage(img, 0, 0, null);
		
		gc.g2d.setTransform(origTrans);
	}

	public static Animation animation(
			String fileName, 
			int rows, int cols,
			double width, double height)
	{
		Animation ani = new Animation();
		
		for(int r = 0; r < rows; r++)
			for(int c = 0; c < cols; c++)
			{
				Sprite spr = new Sprite(
								fileName, r, c, width, height);
				ani.addGraphic(spr);				
			}
		
		return ani;
	}
	
	public void setImage(String fileName)
	{
		this.fileName = fileName;
		
		row = col = -1;
	}
	
	public void setTile(Tile tile)
	{
		fileName = tile.file.getAbsolutePath();

		row = tile.row;
		col = tile.col;
	}
	
	public void setSize(double width, double height) {
		size.width = width; size.height = height;
	}
	
	@Override
	public Rectangle2D.Double getBound()
	{	
		return new Rectangle2D.Double(
				loc.x - size.width/2,
				loc.y - size.height/2,
				size.width, size.height);
	}

	@Override
	public Object clone() {
		return new Sprite(this);
	}	
}