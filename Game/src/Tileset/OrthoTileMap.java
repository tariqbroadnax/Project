package Tileset;

import java.awt.Shape;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Point2D.Double;

import Maths.Dimension2D;

public class OrthoTileMap<E> extends TileMap<E>
{
	@Override
	public Double mapLocation(double x, double y) 
	{
		Dimension2D.Double tileSize = getTileSize();
		
		return new Point2D.Double(
				x / tileSize.width,
				y / tileSize.height);
	}

	@Override
	public Shape tileShape() 
	{
		Dimension2D.Double tileSize = getTileSize();

		Rectangle2D.Double rect = 
				new Rectangle2D.Double(
						0, 0, tileSize.width, 
							  tileSize.height);
		
		return rect;
	}
}
