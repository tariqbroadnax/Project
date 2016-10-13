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
	
	public boolean showsNormalBound(Rectangle2D.Double bound)
	{
		return viewBound.intersects(bound) ||
			   viewBound.contains(bound) ||
			   bound.contains(viewBound);
	}

	public Rectangle2D.Double createNormalBound()
	{
		Dimension screenDim = camera.getScreenDimension(),
				  displayDim = camera.getDisplayDimension();
		
		Point2D.Double focus = camera.getFocus();
		
		double widthRatio = 1.0 * 
				screenDim.width / displayDim.width,
								  
			   heightRatio = 1.0 * 
				screenDim.height / displayDim.height;
							
		return new Rectangle2D.Double(
					focus.x - (widthRatio * 50),
					focus.y - (heightRatio * 50), 
					widthRatio * 100, heightRatio * 100);
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
