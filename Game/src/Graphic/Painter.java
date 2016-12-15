package Graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferStrategy;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

import GUI.GUI;
import Utilities.ImagePool;

public class Painter
{
	private Collection<Paintable> paintables;
			
	private Map<?, ?> renderingHintMap;
	
	private Camera camera;
	
	private ImagePool imgPool;
	
	private JFrame frame;
	private JLayeredPane hud;
	
	public Painter(GUI gui, Camera camera,
				   ImagePool imgPool)
	{
		this.camera = camera;
		this.imgPool = imgPool;
		
		frame = gui.getFrame();
		
		hud = gui.getLayeredPane();
		
		paintables = new LinkedList<Paintable>();
	}
	
	public void paint()
	{
		BufferStrategy strategy =
				frame.getBufferStrategy();

		if(strategy == null) return;
		
		Graphics g = strategy.getDrawGraphics();
		
		Dimension screenDim = frame.getSize();

		g.setColor(Color.white);
		g.fillRect(0, 0, screenDim.width, screenDim.height);
		
		Graphics2D g2d = (Graphics2D)g;

		g2d.setRenderingHint(
				RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.setRenderingHint(
				RenderingHints.KEY_COLOR_RENDERING,
				RenderingHints.VALUE_COLOR_RENDER_QUALITY);
		
		camera.setScreenDimension(screenDim.width, 
								  screenDim.height);

		GraphicsContext gc = 
				new GraphicsContext(
						g2d, camera);
	
		camera.transformGraphics(gc.g2d);

		gc.setImagePool(imgPool);
	
		//for(Paintable p : paintables)
			//p.paint(gc);
		
		
		for(Paintable p : paintables)
			p.paint(gc);	
		
		gc.g2d.setTransform(new AffineTransform());
	
		
		//for(Component comp : frame.getComponents())
			//System.out.println(comp);
		//System.exit(1);
		
		frame.paintComponents(gc.g2d);
		
		gc.g2d.dispose();
		strategy.show();
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	
	public void addPaintable(Paintable p)
	{
		paintables.add(p);
	}
	
	public void removePaintable(Paintable p)
	{
		paintables.remove(p);
	}

}
