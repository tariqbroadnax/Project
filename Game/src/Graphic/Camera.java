package Graphic;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import Maths.Dimension2D;

public class Camera
	extends ComponentAdapter
{
	private Point2D.Double focus;

	// FIXME
	public AffineTransform transform;
	
	private Dimension screenDim,
					  displayDim;
	
	private double targetAspectRatio;
	
	private int maxWidth, maxHeight;
	
	private double zoom;
	
	public Camera()
	{
		focus = new Point2D.Double();
		
		targetAspectRatio = 1.0/1.0;
		
		maxWidth = maxHeight = 400;
		
		displayDim = new Dimension(400, 400);
		screenDim = new Dimension(400, 400);
		
		zoom = 1;
	}
	
	public void setFocus(Point2D.Double focus)
	{
		this.focus = focus;
	}
	
	public void setScreenDimension(Dimension screenDim)
	{
		this.screenDim = screenDim;
	}
	
	public  Dimension getViewDimension()
	{
		double aspectRatio = screenDim.getWidth() / screenDim.getHeight();
		
		Dimension viewDim;
		
		if(aspectRatio < targetAspectRatio)
			viewDim = 
					createDimensionWithWidth(
							targetAspectRatio,
							screenDim.width < maxWidth ? screenDim.width : maxWidth);
		else if(aspectRatio > targetAspectRatio)
			viewDim = 
					createDimensionWithHeight(
							targetAspectRatio, 
							screenDim.height < maxHeight ? screenDim.height : maxHeight);
		else
			viewDim = screenDim;
					
		return viewDim;
	}
	
	private Dimension createDimensionWithWidth(double aspectRatio, int width)
	{
		return new Dimension(
				width,
				(int)(width / aspectRatio));
	}
	
	private Dimension createDimensionWithHeight(double aspectRatio, int height)
	{
		return new Dimension(
				(int)(height * aspectRatio),
				height);		
	}
	
	public void transformGraphics(Graphics2D g2d)
	{				
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

		Dimension viewDim = getViewDimension();

		transform = new AffineTransform();
		
		transform.translate(
				- (focus.x * viewDim.width * zoom / 100.0
						- screenDim.width / 2.0),
				- (focus.y * viewDim.height * zoom / 100.0
						- screenDim.height / 2.0));
		
		transform.scale(zoom, zoom);

		g2d.setTransform(transform);
	}
	
	public Dimension2D.Double screenDimension(Dimension2D.Double normDim)
	{
		return new Dimension2D.Double(
				normDim.width * displayDim.width / 100.0,
				normDim.height * displayDim.height / 100.0);
	}
	
	public Point2D.Double screenLocation(Point2D.Double normLoc)
	{
		return new Point2D.Double(
				normLoc.x * displayDim.width / 100.0,
				normLoc.y * displayDim.height / 100.0);
	}
	
	public Point2D.Double normalLocation(Point screenLoc)
	{		
		Point2D.Double normalLoc = new Point2D.Double();
		
		Dimension viewDim = getViewDimension();

		try
		{
			transform
				.createInverse()		
				.transform(screenLoc, normalLoc);
			
		} catch (Exception e)
		{
			return null;
		}
				
		normalLoc.x *= 100.0 / viewDim.width;
		normalLoc.y *= 100.0 / viewDim.height;
		
		return normalLoc;
	}
	
	public Dimension getScreenDimension()
	{
		return screenDim;
	}
	
	public Dimension getDisplayDimension()
	{
		return displayDim;
	}
	
	public Point2D.Double getFocus()
	{
		return focus;
	}
	
	@Override
	public void componentShown(ComponentEvent e)
	{
		screenDim = e.getComponent().getSize();
	}
	
	@Override
	public void componentResized(ComponentEvent e)
	{
		screenDim = e.getComponent().getSize();
	}
}
