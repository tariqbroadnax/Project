package Graphic;

import static Utilities.ShapeUtilities.dimension;
import static Utilities.ShapeUtilities.offsetToCenter;
import static Utilities.ShapeUtilities.scaleToFit;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

import Maths.Dimension2D;
import Utilities.MathUtilities;

public abstract class Graphic
	implements Cloneable, Serializable
{	
	private boolean visible;
	
	protected Point2D.Double loc;
	
	private Collection<GraphicListener> listeners;
	
	public Graphic()
	{
		visible = true;
		loc = new Point2D.Double();
		listeners = new LinkedList<GraphicListener>();
	}
	
	public Graphic(Graphic graphic)
	{
		visible = graphic.visible;
		loc = new Point2D.Double();
		listeners = new LinkedList<GraphicListener>();
	}
	
	public void update(Duration delta) {}
	
	protected abstract void _paint(GraphicsContext gc);
	
	public abstract void paint(
			Graphics2D g2d, Point screenLoc, 
			Dimension projection);
	
	public void paint(
			Graphics2D g2d, int x, int y, 
			int projWidth, int projHeight)
	{
		Point screenLoc = new Point(x, y);
		Dimension proj = new Dimension(projWidth, projHeight);
		
		paint(g2d, screenLoc, proj);
	}
	
	public final void paint(GraphicsContext gc)
	{
		if(visible && visibleInside(gc))
			_paint(gc);
	}
	
	public void paint(
			GraphicsContext gc,
			Point2D.Double normalPaintLoc)
	{
		Point2D.Double prev = loc;
		
		loc = normalPaintLoc;
		
		paint(gc);
		
		loc = prev;
	}
	
	public BufferedImage projectedImage(int screenW, int screenH)
	{
		Dimension screenDim = new Dimension(screenW, screenH);
		
		return projectedImage(screenDim);
	}
	
	public BufferedImage projectedImage(Dimension screenDim)
	{
		Rectangle2D.Double bound = getBound();
		Dimension2D normalDim = dimension(bound);
	
		double normalW = normalDim.getWidth(),
			   normalH = normalDim.getHeight(),
			   screenW = screenDim.getWidth(),
			   screenH = screenDim.getHeight();
		
		int widthOnScreen = (int)Math.ceil(normalW * screenW / 100),
			heightOnScreen = (int)Math.ceil(normalH * screenH / 100);
		
		BufferedImage img = new BufferedImage(
				widthOnScreen, heightOnScreen,
				BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = (Graphics2D)img.getGraphics();
		
		paint(g2d, 0, 0, widthOnScreen, heightOnScreen);
		
		return img;
	}
	
	public BufferedImage toImage(int imgSideLen)
	{
		Rectangle2D.Double graphicBound = getBound();
		
		Dimension2D.Double boundDim = dimension(graphicBound),
						   scaledDim = scaleToFit(boundDim, imgSideLen),
						   imgDim = new Dimension2D.Double(imgSideLen,
								   						   imgSideLen);
		
		Point2D.Double offsetToCenter = offsetToCenter(scaledDim, imgDim);
		
		Point paintLoc = new Point(
				(int)offsetToCenter.x, (int)offsetToCenter.y);
		
		Dimension paintDim = new Dimension(
				(int)scaledDim.width, (int)scaledDim.height);
		// width height
		
		BufferedImage img = new BufferedImage(
				imgSideLen, imgSideLen,
				BufferedImage.TYPE_INT_ARGB);
		
		Graphics g = img.getGraphics();
		Graphics2D g2d = (Graphics2D)g;
		
		paint(g2d, paintLoc, paintDim);
		
		return img;
	}
	
	private boolean visibleInside(GraphicsContext gc)
	{
		Rectangle2D.Double bound = getBound();
				
		//if(!MathUtilities.visibleInside(gc.viewBound, bound))
			//System.exit(1);
		
		if(bound == null) return true;
		else
		{			
			return MathUtilities.overlaps(gc.createNormalBound(), bound);
		}
	}

	// this method is not necessary but will be used if overwritten
	public abstract Rectangle2D.Double getBound();
	
	public void addGraphicListener(GraphicListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeGraphicListener(GraphicListener listener)
	{
		listeners.remove(listener);
	}
	
	protected void notifyListenerOfFieldChanged()
	{
		for(GraphicListener listener : listeners)
			listener.fieldChanged(this);
	}
	
	public void setLoc(Point2D.Double loc)
	{
		this.loc = new Point2D.Double(loc.x, loc.y);
		notifyListenerOfFieldChanged();
	}
	
	public void setVisible(boolean visible)
	{
		this.visible = visible;
		notifyListenerOfFieldChanged();
	}
	
	public Point2D.Double getLoc()
	{
		return loc;
	}
	
	public Object clone()
	{
		return _clone();
	}
	
	public boolean isVisible()
	{
		return visible;
	}
	
	protected abstract Graphic _clone();
}
