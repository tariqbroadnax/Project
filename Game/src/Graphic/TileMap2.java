package Graphic;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import Maths.Dimension2D;

public class TileMap2 extends Graphic
{
	private Dimension2D.Double tileDim;
	
	private int[][] imgIDs;
	
	public TileMap2()
	{
		setLoc(new Point2D.Double(-200, -200));
		
		tileDim = new Dimension2D.Double(10, 10);
		
		imgIDs = new int[40][40];
		
		for(int row = 0; row < 40; row++)
			for(int col = 0; col < 40; col++)
				imgIDs[col][row] = 0;
	}
	
	public void set(int index, int imageID)
	{
		int col = index % cols(),
			row = index / cols();
		
		set(col, row, imageID);
	}
	
	public void set(int col, int row, int imageID)
	{
		imgIDs[col][row] = imageID;
	}
	
	private int rows()
	{
		return imgIDs.length;
	}
	
	private int cols()
	{
		return imgIDs[0].length;
	}
	
	@Override
	protected void _paint(GraphicsContext gc) 
	{				
		Point2D.Double screenLoc = gc.camera.screenLocation(loc);
		Dimension2D.Double screenDim = gc.camera.screenDimension(tileDim);
			
		Rectangle2D.Double normalBound = gc.createNormalBound();
		
		int startCol = (int)((normalBound.x - loc.x) / tileDim.width),
			startRow = (int)((normalBound.y - loc.y) / tileDim.height),
			endCol = startCol + (int)Math.ceil(normalBound.width / tileDim.width) + 1,
			endRow = startRow + (int)Math.ceil(normalBound.height / tileDim.height) + 1;
		
		startCol = startCol < 0 ? 0 : startCol;
		startRow = startRow < 0 ? 0 : startRow;
		
		endCol = endCol > cols() ? cols() : endCol;
		endRow = endRow > rows() ? rows() : endRow;
				
		for(int row = startRow; row < endRow; row++)
		{
			for(int col = startCol; col < endCol; col++)
			{	
				Image img = gc.getImagePool()
							  .get(imgIDs[col][row]);
				
				gc.g2d.drawImage(img, (int)(screenLoc.x + col * screenDim.width),
									  (int)(screenLoc.y + row * screenDim.height),
									  (int)(screenDim.width),
									  (int)(screenDim.height), null);

			}	
		}
		
		//gc.g2d.setTransform(transform);
	}
	
	public Point2D.Double tileLocation(Point2D.Double loc)
	{
		return tileLocation(index(loc));
	}
	
	public Point2D.Double tileLocation(int index)
	{
		int col = index % cols(),
			row = index / cols();
		
		return new Point2D.Double(
				loc.x + col * tileDim.width,
				loc.y + row * tileDim.height);
	}
	
	public int index(Point2D.Double loc)
	{
		double x = loc.x - this.loc.x,
			   y = loc.y - this.loc.y;
		
		int col = (int)(x / tileDim.width),
			row = (int)(y / tileDim.height);
		
		col = col < 1 && x < 0 ? col - 1 : col;
		row = row < 1 && y < 0 ? row - 1 : row;
				
		return row * cols() + col;
	}

	public Dimension2D.Double getTileDimension()
	{
		return tileDim;
	}
	
	@Override
	public Double getBound() {
		return null;
	}

	@Override
	protected Graphic _clone() {
		return null;
	}
}
