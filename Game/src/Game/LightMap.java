package Game;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedList;

import Graphic.AddComposite;
import Graphic.GraphicsContext;

public class LightMap implements Serializable
{	
	private Color ambientColor;
	
	private Collection<Light> lights;
	
	private BufferedImage img;
	private Graphics2D g2d;
	private boolean mapUpdated;
	
	public LightMap()
	{
		ambientColor = new Color(0, 0, 0, 120);
	
		lights = new LinkedList<Light>();
		
		mapUpdated = false;
		
		lights.add(new Light());	
		lights.add(new Light(50, 50));		
		lights.add(new Light(25, 25));		

	
		img = new BufferedImage(800, 600,
								BufferedImage.TYPE_INT_ARGB);
	
		g2d = (Graphics2D)img.getGraphics();
	}
	
	public void paint(GraphicsContext gc)
	{	
		if(!mapUpdated)
		{
			paintAmbientBackground();
			paintLights(gc);
			mapUpdated = true;
		}
		
		paintOntoGC(gc);
	}

	private void paintAmbientBackground()
	{
		int imgWidth = img.getWidth(),
			imgHeight = img.getHeight();
		
		g2d.setBackground(ambientColor);
		g2d.clearRect(0, 0, imgWidth, imgHeight);
	}
	
	private void paintLights(GraphicsContext gc)
	{
		Graphics2D g2d = (Graphics2D) this.g2d.create();
		AffineTransform cameraTrans =
				gc.g2d.getTransform();
		
	//	g2d.setComposite(AlphaComposite.DstOut);
	//	g2d.setComposite(AddComposite.instance);
		g2d.setTransform(cameraTrans);
		
		for(Light light : lights)
			light.paint(gc.camera, g2d);
		
		g2d.dispose();
	}
	
	private void paintOntoGC(GraphicsContext gc)
	{
		Graphics2D g = (Graphics2D) gc.g2d.create();
		
		g.setTransform(new AffineTransform());
		g.drawImage(img, 0, 0, null);
	}
	
	public void add(Light light) {
		lights.add(light);
		mapUpdated = false;
	}
	
	public void remove(Light light) {
		lights.remove(light);
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
}