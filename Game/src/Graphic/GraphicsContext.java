package Graphic;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import Maths.Dimension2D;
import Utilities.ImagePool;

public class GraphicsContext
{
	public final Graphics2D g2d;
	
	public final Camera camera;
	
	private ImagePool imgPool;
	
	public GraphicsContext(
			Graphics2D g2d,
			Camera camera)
	{
		this.g2d = g2d;
		this.camera = camera;	
	}
	
	public GraphicsContext(
			Graphics2D g2d,
			Camera camera,
			ImagePool imgPool)
	{
		this.g2d = g2d;
		this.camera = camera;
		this.imgPool = imgPool;
		
		camera.transformGraphics(g2d);
	}
	
	public void setImagePool(ImagePool imgPool)
	{
		this.imgPool = imgPool;
	}
	
	public ImagePool getImagePool()
	{
		return imgPool;
	}
}
