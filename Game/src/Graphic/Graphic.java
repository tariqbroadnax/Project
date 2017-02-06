package Graphic;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.Duration;

import Maths.Dimension2D;

public abstract class Graphic
	implements Cloneable, Serializable
{	
	private boolean visible;
	
	protected Point2D.Double loc;
	
	protected int z;
		
	public Graphic()
	{
		visible = true;
		loc = new Point2D.Double();
		z = 0;
	}
	
	public Graphic(Graphic graphic)
	{
		visible = graphic.visible;
		loc = new Point2D.Double();
		z = graphic.z;
	}
	
	public void update(Duration delta) {}
	
	protected abstract void _paint(GraphicsContext gc);
	
	public final void paint(GraphicsContext gc)
	{
		if(visible && visibleInside(gc))
			_paint(gc);
	}
	
	public BufferedImage projectedImage(int screenW, int screenH)
	{
		Dimension screenDim = new Dimension(screenW, screenH);
		
		return projectedImage(screenDim);
	}
	
	public BufferedImage projectedImage(Dimension screenSize)
	{
		Camera camera = new Camera();
		
		camera.setFocus(loc);
		camera.setScreenDimension(screenSize);
		
		Dimension2D.Double normSize = getSize(),
						   imgSize = camera.sizeOnScreen2D(screenSize.width,
								   						   screenSize.height);
		
		BufferedImage img = new BufferedImage(
				(int)imgSize.width, (int)imgSize.height,
				BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = (Graphics2D)img.getGraphics();
				
		GraphicsContext gc = new GraphicsContext(g2d, camera);
		
		paint(gc);
		
		return img;
	}
	
	public boolean visibleInside(GraphicsContext gc)
	{
		Rectangle2D.Double bound = getBound();
											   
		if(bound == null) 
			return true;
		else			
			return gc.camera.shows(bound);
	}

	public Dimension2D.Double getSize()
	{
		Rectangle2D.Double bound = getBound();
		
		if(bound != null)
			return new Dimension2D.Double(
				bound.width, bound.height);
		else
			return null;
	}

	public abstract Rectangle2D.Double getBound();
	
	public void setLoc(Point2D.Double loc) {
		setLoc(loc.x, loc.y);
	}
	
	public void setLoc(double x, double y) {
		loc.x = x; loc.y = y;
	}
	
	public void setLayer(int z) {
		this.z = z;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public Point2D.Double getLoc() {
		return new Point2D.Double(loc.x, loc.y);
	}

	public boolean isVisible() {
		return visible;
	}
	
	public int getLayer() {
		return z;
	}
	
	public abstract Object clone();
}
