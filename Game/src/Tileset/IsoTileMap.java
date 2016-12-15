package Tileset;

import java.awt.Shape;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;

import Maths.Dimension2D;

public class IsoTileMap extends TileMap
{
	@Override
	public Point2D.Double mapLocation(double x, double y) 
	{		
		Dimension2D.Double tileSize = getTileSize();
		
		return new Point2D.Double(
				x / tileSize.width + 
				y / tileSize.height,
				y / tileSize.height - 
				x / tileSize.width);
	}
	
	@Override
	public Shape tileShape() 
	{
		Dimension2D.Double tileSize = getTileSize();

		Path2D.Double path = new Path2D.Double();
		
		path.moveTo(tileSize.width/2, 0);
		path.lineTo(tileSize.width, tileSize.height/2);
		path.lineTo(tileSize.width/2, tileSize.height);
		path.lineTo(0, tileSize.height/2);
		
		return path;
	}
}
