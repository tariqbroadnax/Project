package Graphic;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.Observable;

import Maths.Dimension2D;
import Maths.Maths;
import Maths.Vector2D;

public class Camera extends Observable
{
	private Point2D.Double focus;
	
	private Dimension screenSize,
					  displaySize;
	
	private double targetAspectRatio;
	
	private int maxWidth, maxHeight;
	
	private double zoom;
	
	public Camera()
	{
		focus = new Point2D.Double();
		
		targetAspectRatio = 1.0/1.0;
		
		maxWidth = maxHeight = 400;
		
		displaySize = new Dimension(400, 400);
		screenSize = new Dimension(400, 400);
		
		zoom = 1;
	}

	public void transformGraphics(Graphics2D g2d)
	{				
		g2d.setRenderingHint(
				RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		AffineTransform transform = 
				g2d.getTransform();
	
		formatTransform(transform);
		
		g2d.setTransform(transform);
	}

	public boolean shows(Rectangle2D.Double normalBound)
	{
		Rectangle2D.Double normalViewBound = 
						   normalViewBound();
		
		return Maths.overlaps(normalBound, normalViewBound);
	}
	
	public Dimension viewSize()
	{
		double aspectRatio = screenSize.getWidth() / 
							 screenSize.getHeight();
		
		Dimension viewSize;
		
		if(aspectRatio < targetAspectRatio)
			viewSize = createDimensionWithWidth(
							targetAspectRatio,
							screenSize.width < maxWidth ? 
							screenSize.width : maxWidth);
		else if(aspectRatio > targetAspectRatio)
			viewSize = createDimensionWithHeight(
							targetAspectRatio, 
							screenSize.height < maxHeight ? 
							screenSize.height : maxHeight);
		else
			viewSize = screenSize;
					
		return viewSize;
	}
	
	public Dimension2D.Double normalViewSize()
	{
		Dimension viewSize = viewSize();
		
		double normalWidth = 100.0 * screenSize.width /
								     viewSize.width,
			   normalHeight = 100.0 * screenSize.height /
			   						  viewSize.height;
		
		return new Dimension2D.Double(normalWidth,
									  normalHeight);
	}
	
	private Dimension createDimensionWithWidth(
			double aspectRatio, int width)
	{
		return new Dimension(
				width,
				(int)Math.ceil(width / aspectRatio));
	}
	
	private Dimension createDimensionWithHeight(
			double aspectRatio, int height)
	{
		return new Dimension(
				(int)Math.ceil(height * aspectRatio),
				height);		
	}
	
	public double screenHeight(double height)
	{
		Dimension viewSize = viewSize();

		return height * viewSize.height / 100;
	}
	
	public Dimension2D.Double sizeOnScreen(
			double width, double height)
	{	
		Dimension viewSize = viewSize();

		return new Dimension2D.Double(
				width * viewSize.width / 100.0,
				height * viewSize.height / 100.0);
	}
	
	public RectangularShape screenShape(
			RectangularShape in, RectangularShape out)
	{
		if(out == null)
			out = (RectangularShape)in.clone();
	
		double x = in.getX(),
			   y = in.getY(),
			   w = in.getWidth(),
			   h = in.getHeight();
		
		Dimension viewSize = viewSize();

		x *= viewSize.width / 100.0;
		y *= viewSize.height / 100.0;
		w *= viewSize.width / 100.0;
		h *= viewSize.height / 100.0;
		
		out.setFrame(x, y, w+1, h+1);
		
		return out;
	}
	
	
	public Dimension2D.Double sizeOnScreen(
			Dimension2D.Double normDim)
	{
		return sizeOnScreen(normDim.width, normDim.height);
	}
	
	public Rectangle onScreenBound(
			Rectangle2D.Double normalBound)
	{
		Point screenLoc = screenLocation3(
				normalBound.x, normalBound.y);
		
		Dimension onScreenSize = 
				sizeOnScreen(
						normalBound.width, normalBound.height)
						.ceil();
		
		return new Rectangle(
				screenLoc.x, screenLoc.y,
				onScreenSize.width, onScreenSize.height);
	}
	
	public Point2D.Double screenLocation(
			Point2D.Double normLoc)
	{
		return screenLocation(normLoc.x, normLoc.y);
	}
	
	public Point2D.Double screenLocation(
			double x, double y)
	{
		Dimension viewSize = viewSize();

		return new Point2D.Double(
				x * viewSize.width / 100.0,
				y * viewSize.height / 100.0);
	}
	
	public Point2D.Double screenLocation2(
			Point2D.Double normLoc)
	{
		Dimension viewSize = viewSize();
		
		return new Point2D.Double(
				(normLoc.x - focus.x) * viewSize.width / 100.0 +
				screenSize.width/2,
				(normLoc.y - focus.y) * viewSize.height / 100.0 +
				screenSize.height/2);
	}
	
	public Point screenLocation3(double x, double y)
	{
		Point2D.Double normLoc = new Point2D.Double(x, y);
		Point2D.Double screenLoc = screenLocation(normLoc);
		
		return new Point((int)screenLoc.x, (int)screenLoc.y);
	}
	
	public Point screenLocation3(Point2D.Double normLoc)
	{
		Point2D.Double screenLoc = screenLocation2(normLoc);
		
		return new Point((int)screenLoc.x, (int)screenLoc.y);
	}
	
	public Point2D.Double normalLocation(Point screenLoc)
	{		
		AffineTransform transform =
				new AffineTransform();
		
		formatTransform(transform);
		
		Point2D.Double normalLoc = new Point2D.Double();
		
		Dimension viewSize = viewSize();

		try
		{
			transform
				.createInverse()
				.transform(screenLoc, normalLoc);
		} catch (Exception e)
		{
			return null;
		}
				
		normalLoc.x *= 100.0 / viewSize.width;
		normalLoc.y *= 100.0 / viewSize.height;
		
		return normalLoc;
	}
	
	public Dimension2D.Double normalSize(Dimension size)
	{
		Dimension viewSize = viewSize();

		return new Dimension2D.Double(
				100 * size.width / viewSize.width,
				100 * size.height / viewSize.height);
	}
	
	public Vector2D.Double normalVector(int x, int y)
	{
		Dimension viewSize = viewSize();

		return new Vector2D.Double(
				100 * x / viewSize.width,
				100 * y / viewSize.height);
	}

	public Rectangle2D.Double normalViewBound()
	{
		Dimension2D.Double normalScreenSize = 
				normalSize(screenSize);
		
		return new Rectangle2D.Double(
				focus.x - normalScreenSize.width/2,
				focus.y - normalScreenSize.height/2,
				normalScreenSize.width,
				normalScreenSize.height);
	}
	
	public Rectangle2D.Double viewBound()
	{
		Point2D.Double focus = focusOnScreen();
		
		return new Rectangle2D.Double(
				focus.x - screenSize.width/2,
				focus.y - screenSize.height/2,
				screenSize.width,
				screenSize.height);
	}
	
	private void formatTransform(
			AffineTransform transform)
	{
		Point2D.Double focusOnScreen =
				focusOnScreen();
		
		transform.translate(
				-focusOnScreen.x, -focusOnScreen.y);
	
		transform.scale(zoom, zoom);

		transform.translate(
				screenSize.width/2, screenSize.height/2);		
	}
	
	private Point2D.Double focusOnScreen()
	{
		Dimension viewSize = viewSize();
		
		return new Point2D.Double(
				focus.x * viewSize.width / 100,
				focus.y * viewSize.height / 100);
	}

	public void setDisplaySize(Dimension displaySize)
	{
		setDisplaySize(displaySize.width,
					   displaySize.height);	
	}
	
	public void setDisplaySize(int width, int height)
	{
		displaySize.width = width;
		displaySize.height = height;
		
		setChanged();
		notifyObservers();
	}

	public void setScreenSize(Dimension screenSize)
	{
		setScreenDimension(screenSize.width,
						   screenSize.height);
	}
	
	public void setScreenDimension(int width, int height)
	{
		screenSize.width = width;
		screenSize.height = height;
		
		setChanged();
		notifyObservers();
	}
	
	public void setScreenDimension(Dimension size)
	{
		setScreenDimension(size.width, size.height);
	}

	public void setFocus(Point2D.Double focus)
	{
		// copies references to allow camera to move with focus		
		this.focus = focus;
		
		setChanged();
		notifyObservers();
	}
	
	public void setFocus(double x, double y)
	{
		Point2D.Double focus = new Point2D.Double(x, y);
		
		setFocus(focus);
	}	
	
	public void moveFocus(Vector2D.Double shift)
	{
		moveFocus(shift.x, shift.y);
	}
	
	public void moveFocus(double x, double y)
	{
		focus = new Point2D.Double(
				focus.x + x, focus.y + y);
	}
	
	public Point2D.Double getFocus()
	{
		return new Point2D.Double(
				focus.x, focus.y);
	}
	
	public Dimension getScreenSize()
	{
		return new Dimension(screenSize.width,
							 screenSize.height);
	}
}
