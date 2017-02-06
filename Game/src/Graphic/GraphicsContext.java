package Graphic;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import Utilities.ImagePool;

public class GraphicsContext
{
	public final Graphics2D g2d;
	
	public final Camera camera;
	
	public GraphicsContext(Graphics g)
	{
		this(g, new Camera());
	}
	
	public GraphicsContext(Graphics g, Dimension screenSize)
	{
		this(g, new Camera(screenSize.width, screenSize.height));
	}	
	
	public GraphicsContext(
			Graphics g, Camera camera)
	{
		this.g2d = (Graphics2D) g.create();
		this.camera = camera;
		
		camera.transformGraphics(g2d);
	}
}
