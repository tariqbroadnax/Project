package Graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

import Utilities.TileMap;

public class TileMapGraphic extends Graphic implements NormalGraphic
{
	private TileMap map;
	
	public TileMapGraphic(TileMap map)
	{
		this.map = map;
	}
	
	@Override
	protected Graphic _clone()
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void _paint(GraphicsContext gc) 
	{
		Point2D.Double newLoc = NormalGraphic.findScreenLoc(
				map.getStartLocation(), gc.screenDim);
		
		double w = map.getTileWidth() * gc.viewDim.width / 100,
			   h = map.getTileHeight() * gc.viewDim.height / 100;
		
		gc.g2d.setFont(new Font("Consolas", Font.PLAIN, 12));
		gc.g2d.setColor(Color.black);
		
		for(int r = 0; r < map.getRowCount(); r++)
			for(int c = 0; c < map.getColumnCount(); c++)
			{
				gc.g2d.drawRect((int)(loc.x + c * w),
								(int)(loc.y + r * h) - 15,
								(int)w, (int)h);
				
				gc.g2d.drawString("<" + c + "," + r + ">",
								 (int)(loc.x + c * w),
								 (int)(loc.y + r * h));
			}
		// TODO Auto-generated method stub
		
	}

	@Override
	public void paint(Point screenLoc, Dimension dim, Graphics2D g2d) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public java.awt.geom.Rectangle2D.Double getBound() {
		// TODO Auto-generated method stub
		return null;
	}

}
