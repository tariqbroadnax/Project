package Game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.Area;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import Graphic.GraphicsContext;
import Maths.Circle2D;
import Maths.Maths;

public class LightMap implements Serializable
{	
	private Color ambientColor;
	
	private Collection<Light> lights;
	
	private BufferedImage img;
	private Graphics2D g2d;
	
	public LightMap()
	{
		ambientColor = new Color(0, 0, 0, 120);
	
		lights = new LinkedList<Light>();
		
		lights.add(new Light());		
	
		img = new BufferedImage(800, 600,
								BufferedImage.TYPE_INT_ARGB);
	
		g2d = (Graphics2D)img.getGraphics();
	}
	
	public void setAmbientColor(Color color)
	{
		ambientColor = color;
	}
	
	public void setSize(Dimension size)
	{
		if(size.width == 0 || size.height == 0)
			return;
				
		img = new BufferedImage(size.width, size.height,
				BufferedImage.TYPE_INT_ARGB);
		
		g2d = (Graphics2D)img.getGraphics();
	}
	
	public void paint(GraphicsContext gc)
	{		
		Rectangle2D.Double viewBound =
				gc.camera.viewBound();
		
		Area area = new Area(viewBound);
	
		g2d.setComposite(AlphaComposite.Src);
		g2d.setColor(ambientColor);
		g2d.fillRect(0, 0, img.getWidth(),img.getHeight());
		
		g2d.setComposite(AlphaComposite.DstOut);
		g2d.setTransform(gc.g2d.getTransform());
		for(Light light : lights)
		{
			Circle2D.Double lightBound =
					light.screenBound(gc.camera);
			
			if(Maths.overlaps(lightBound, viewBound))
			{
				area.subtract(new Area(lightBound));
				light.paint(gc.camera, g2d);
			}
		}	
		
		g2d.setTransform(new AffineTransform());

		Graphics2D g = (Graphics2D) gc.g2d.create();
		g.setTransform(new AffineTransform());
		g.drawImage(img, 0, 0, null);
		
		// gc.g2d.fillRect(200, 200, 20, 20);
	}
	
	public void add(Light light) {
		lights.add(light);
	}
	
	public void remove(Light light) {
		lights.remove(light);
	}
}
