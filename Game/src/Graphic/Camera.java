package Graphic;

import static java.lang.Math.ceil;
import static java.lang.Math.round;

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

public class Camera
{
	private Point2D.Double focus;
	
	private Dimension screenSize,
					  displaySize;
	
	private double targetAspectRatio;
	
	private int maxWidth, maxHeight;
	
	private double zoom;
	
	public Camera()
	{
		this(800, 600);
	}
	
	public Camera(int screenW, int screenH)
	{
		screenSize = new Dimension(screenW, screenH);
		
		focus = new Point2D.Double(0, 0);
		
		targetAspectRatio = 1.0/1.0;
		
		maxWidth = maxHeight = 400;
		
		displaySize = new Dimension(400, 400);
		
		zoom = 1;
	}

	public void transformGraphics(Graphics2D g2d)
	{				
		// FIXME
		g2d.setRenderingHint(
				RenderingHints.KEY_INTERPOLATION,
				RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		
		AffineTransform transform = g2d.getTransform();
	
		formatTransform(transform);
		g2d.setTransform(transform);
	}

	public boolean shows(Rectangle2D.Double normalBound)
	{	
		Rectangle2D.Double normScrBound = normalScreenBound();
		
		
		return Maths.overlaps(normalBound, normScrBound);
	}
	
	public Rectangle2D.Double normalScreenBound()
	{
		Dimension2D.Double normScrSize = normalSize(screenSize);
		
		Rectangle2D.Double bound = new Rectangle2D.Double(
				focus.x - normScrSize.width/2,
				focus.y - normScrSize.height/2,
				normScrSize.width, normScrSize.height);
		
		return bound;
	}
	
	public Rectangle screenBound()
	{
		Point2D.Double focus = focusOnScreen();
		
		return new Rectangle(
				(int)(focus.x - screenSize.width/2.0),
				(int)(focus.y - screenSize.height/2.0),
				screenSize.width, screenSize.height);
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
		
		double normW = 100.0 * screenSize.width / viewSize.width / zoom,
			   normH = 100.0 * screenSize.height / viewSize.height / zoom;
		
		return new Dimension2D.Double(normW, normH);
	}
	
	private Dimension createDimensionWithWidth(
			double aspectRatio, int width)
	{
		return new Dimension(
				width, (int)ceil(width / aspectRatio));
	}
	
	private Dimension createDimensionWithHeight(
			double aspectRatio, int height)
	{
		return new Dimension(
				(int)ceil(height * aspectRatio), height);		
	}
	
	public double screenWidth(double width)
	{
		Dimension viewSize = viewSize();

		return width * viewSize.width / 100.0;
	}
	
	public double screenHeight(double height)
	{
		Dimension viewSize = viewSize();

		return height * viewSize.height / 100.0;
	}
	
	public Dimension2D.Double sizeOnScreen2D(
			double width, double height)
	{	
		Dimension viewSize = viewSize();

		return new Dimension2D.Double(
				width * viewSize.width / 100.0 ,
				height * viewSize.height / 100.0);
	}
	
	public Dimension sizeOnScreen(
			double width, double height)
	{	
		Dimension2D.Double scrSize2D = 
				sizeOnScreen2D(width, height);
		
		return new Dimension(
				(int)ceil(scrSize2D.width),
				(int)ceil(scrSize2D.height));
	}
	
	public Dimension sizeOnScreen(
			Dimension2D.Double normDim)
	{
		return sizeOnScreen(normDim.width, normDim.height);
	}
		
	public Dimension2D.Double sizeOnScreen2D(
			Dimension2D.Double normDim)
	{
		return sizeOnScreen2D(normDim.width, normDim.height);
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
	
	public Rectangle boundOnScreen(
			double normX, double normY,
			double normWidth, double normHeight)
	{
		Point scrLoc = screenLocation(normX, normY);
		
		Dimension scrSize = sizeOnScreen(normWidth, normHeight);
		
		return new Rectangle(scrLoc.x, scrLoc.y,
							 scrSize.width, scrSize.height);
	}
	
	public Rectangle2D.Double boundOnScreen2D(
			double normX, double normY,
			double normWidth, double normHeight)
	{
		Point2D.Double scrLoc = screenLocation2D(normX, normY);
		
		Dimension2D.Double scrSize = sizeOnScreen2D(normWidth, normHeight);
		
		return new Rectangle2D.Double(scrLoc.x, scrLoc.y,
									  scrSize.width, scrSize.height);
	}
	
	public Rectangle boundOnScreen(
			Rectangle2D.Double normBound)
	{
		return boundOnScreen(normBound.x, normBound.y,
							 normBound.width, normBound.height);
	}
	
	public Rectangle shiftedBoundOnScreen(
			double normX, double normY, double normW, double normH)
	{
		Point shiftScrLoc = shiftedScreenLocation(
				normX, normY);
		
		Dimension scrSize = sizeOnScreen(
				normW, normH);
		
		return new Rectangle(shiftScrLoc.x, shiftScrLoc.y,
							 scrSize.width, scrSize.height);
	}
	
	
	public Rectangle shiftedBoundOnScreen(
			Rectangle2D.Double normBound)
	{
		return shiftedBoundOnScreen(normBound.x, normBound.y,
									normBound.width, normBound.height);
	}
	
	public Point2D.Double screenLocation2D(
			Point2D.Double normLoc)
	{
		return screenLocation2D(normLoc.x, normLoc.y);
	}
	
	public Point2D.Double screenLocation2D(
			double x, double y)
	{
		Dimension viewSize = viewSize();

		return new Point2D.Double(
				x * viewSize.width / 100.0,
				y * viewSize.height / 100.0);
	}
	
	public Point2D.Double shiftedScreenLocation2D(
			double x, double y)
	{
		Dimension viewSize = viewSize();
		
		return new Point2D.Double(
				(x - focus.x) * viewSize.width / 100.0 +
				screenSize.width/2,
				(y - focus.y) * viewSize.height / 100.0 +
				screenSize.height/2);
	}
	
	
	public Point2D.Double shiftedScreenLocation2D(
			Point2D.Double normLoc)
	{
		return shiftedScreenLocation2D(normLoc.x, normLoc.y);
	}
	
	public Point screenLocation(double x, double y)
	{
		Dimension viewSize = viewSize();

		return new Point((int)round(x * viewSize.width / 100.0),
						 (int)round(y * viewSize.height / 100.0));
	}
	
	public Point shiftedScreenLocation(double normX, double normY)
	{
		Point2D.Double shiftScreenLoc2D = 
				screenLocation2D(normX, normY);
		
		return new Point((int)round(shiftScreenLoc2D.x),
						 (int)round(shiftScreenLoc2D.y));
	}
	
	public Point shiftedScreenLocation(Point2D.Double normLoc)
	{
		return shiftedScreenLocation(normLoc.x, normLoc.y);
	}
	
	public double normalY(int y)
	{
		Point p = new Point(0, y);
		
		return normalLocation(p).y;
	}
	
	public double normalX(int x)
	{
		Point p = new Point(x, 0);
		
		return normalLocation(p).x;
	}
	
	public Point2D.Double normalLocation(Point screenLoc)
	{		
		AffineTransform transform =
				new AffineTransform();
		
		formatTransform(transform);
		
		Point2D.Double normalLoc = new Point2D.Double();
		
		Dimension viewSize = viewSize();

		try {
			transform
				.createInverse()
				.transform(screenLoc, normalLoc);
		} catch (Exception e) {
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
				100 * size.width / viewSize.width / zoom,
				100 * size.height / viewSize.height / zoom);
	}
	
	public double normalWidth(double width)
	{
		Dimension2D.Double normViewSize = normalViewSize();
		
		return 100 * width / normViewSize.width;
	}
	
	public double normalHeight(double height)
	{
		Dimension2D.Double normViewSize = normalViewSize();
		
		return 100 * height / normViewSize.height;
	}
	
	public Vector2D.Double normalVector(int x, int y)
	{
		Dimension2D.Double normViewSize = normalViewSize();

		return new Vector2D.Double(
				100 * x / normViewSize.width,
				100 * y / normViewSize.height);
	}

	public Rectangle2D.Double normalViewBound()
	{
		Dimension2D.Double normViewSize = normalViewSize();
		
		return new Rectangle2D.Double(
				focus.x - normViewSize.width/2,
				focus.y - normViewSize.height/2,
				normViewSize.width,
				normViewSize.height);
	}
	
	public Rectangle2D.Double viewBound()
	{
		Point2D.Double focus = focusOnScreen();
		
		Dimension view = viewSize();
		
		return new Rectangle2D.Double(
				focus.x - view.width/2,
				focus.y - view.height/2,
				view.width, view.height);
	}
	
	private void formatTransform(
			AffineTransform transform)
	{
		Point2D.Double focusOnScreen =
				focusOnScreen();
		
		transform.translate(
				-focusOnScreen.x, -focusOnScreen.y);

		transform.translate(
				screenSize.width/2, screenSize.height/2);		

		transform.scale(zoom, zoom);
	}
	
	private Point2D.Double focusOnScreen()
	{
		Dimension viewSize = viewSize();
		
		return new Point2D.Double(
				zoom * focus.x * viewSize.width / 100,
				zoom * focus.y * viewSize.height / 100);
	}

	public void setZoom(double zoom) {
		this.zoom = zoom;
	}
	
	public double getZoom() {
		return zoom;
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
	}
	
	public void setScreenDimension(Dimension size)
	{
		setScreenDimension(size.width, size.height);
	}

	public void setFocus(Point2D.Double focus)
	{
		// copies references to allow camera to move with focus		
		this.focus = focus;
	}
	
	public void setFocus(double x, double y)
	{
		Point2D.Double focus = new Point2D.Double(x, y);
		
		setFocus(focus);
	}
	
	public void setFocusTL(double x, double y)
	{
		Rectangle2D.Double bound = normalScreenBound();
		
		setFocus(x + bound.width/2,
				 y + bound.height/2);
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
	
	public void setTargetAspectRatio(double targetAspectRatio)
	{
		this.targetAspectRatio = targetAspectRatio;
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
