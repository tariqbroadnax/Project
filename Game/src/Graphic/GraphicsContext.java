package Graphic;

import java.awt.Graphics;
import java.awt.Graphics2D;

import Utilities.ImagePool;

public class GraphicsContext
{
	public final Graphics2D g2d;
	
	public final Camera camera;
	
	public final ImagePool imgPool;
	
	public GraphicsContext(Graphics g)
	{
		this(g, new Camera());
	}
	
	public GraphicsContext(
			Graphics g, Camera camera)
	{
		this(g, camera, new ImagePool());
	}
	
	public GraphicsContext(
			Graphics g, Camera camera,
			ImagePool imgPool)
	{
		this.g2d = (Graphics2D) g.create();
		this.camera = camera;
		this.imgPool = imgPool;
		
		camera.transformGraphics(g2d);
	}
}
