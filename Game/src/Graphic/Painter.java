package Graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.RenderingHints;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import GUI.GPanel;

public class Painter
{
	private List<Paintable> paintables;
			
	private Map<Object, Object> hints;
	
	private Camera camera;
	
	private Window window;
	
	private GPanel panel;
	
	private boolean enabled = true;
	
	public Painter(Window window, Camera camera)
	{
		this.window = window;
		this.camera = camera;
				
		hints = new HashMap<Object, Object>();
		
		hints.put(RenderingHints.KEY_ANTIALIASING,
				  RenderingHints.VALUE_ANTIALIAS_ON);
		
		hints.put(RenderingHints.KEY_RENDERING,
				  RenderingHints.VALUE_RENDER_QUALITY);
		
		paintables = new ArrayList<Paintable>();
	}
	
	public Painter(GPanel panel, Camera camera)
	{
		this.panel = panel;
		this.camera = camera;
				
		paintables = new ArrayList<Paintable>();
	}
	
	private void paintPanel()
	{
		Graphics g = panel.getBufferGraphics();
		Dimension size = panel.getSize();
		
		if(g == null) return;
		
		paintPs(size, g);
		
		try {
			EventQueue.invokeAndWait(() -> panel.paintComponents(g));
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		panel.showBuffer();
	}
	
	private void paintPs(Dimension size, Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0, 0, size.width, size.height);

		camera.setScreenDimension(size.width, size.height);
		
		GraphicsContext gc = new GraphicsContext(g, camera);

		gc.g2d.setRenderingHints(hints);
		
		for(Paintable p : paintables)
			p.paint(gc);	
	}
	
	private void paintWindow()
	{
		BufferStrategy strategy = window.getBufferStrategy();
		Dimension size = window.getSize();
								
		if(strategy == null || size.width == 0 || size.height == 0) return;
		
		do {
			
			do {
				Graphics g = strategy.getDrawGraphics();				
				
				paintPs(size, g);

// comment this out if no comps are added
// else the default comp will hide background
				try {
					EventQueue.invokeAndWait(() -> window.paintComponents(g));
				} catch (InvocationTargetException e) {
					System.out.println(e.getMessage());
					e.printStackTrace();
					System.exit(1);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				//window.paintComponents(g);					
			} while(strategy.contentsRestored());
						
			strategy.show();
			
		} while(strategy.contentsLost());
	}
	
	public void paint()
	{
		if(panel != null)
			paintPanel();
		else
			paintWindow();
	}	
		
	
	public void addPaintable(Paintable p) {
		paintables.add(p);
	}
	
	public void removePaintable(Paintable p) {
		paintables.remove(p);
	}
	
	public void swapPaintable(Paintable p, Paintable p2)
	{
		int index = paintables.indexOf(p);
		
		paintables.set(index, p2);
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public void clear() {
		paintables.clear();
	}
	
	public void setToPanel(GPanel panel) {
		this.panel = panel;
		window = null;
	}
	
	public void setToWindow(Window window) {
		this.window = window;
		panel = null;
	}
}
