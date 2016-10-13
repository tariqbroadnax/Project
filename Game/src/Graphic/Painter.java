package Graphic;

import static Utilities.Pack.map;
import static Utilities.Pack.pack;
import static java.awt.RenderingHints.KEY_ALPHA_INTERPOLATION;
import static java.awt.RenderingHints.KEY_COLOR_RENDERING;
import static java.awt.RenderingHints.KEY_DITHERING;
import static java.awt.RenderingHints.KEY_FRACTIONALMETRICS;
import static java.awt.RenderingHints.KEY_INTERPOLATION;
import static java.awt.RenderingHints.KEY_RENDERING;
import static java.awt.RenderingHints.KEY_STROKE_CONTROL;
import static java.awt.RenderingHints.KEY_TEXT_ANTIALIASING;
import static java.awt.RenderingHints.VALUE_ALPHA_INTERPOLATION_QUALITY;
import static java.awt.RenderingHints.VALUE_COLOR_RENDER_QUALITY;
import static java.awt.RenderingHints.VALUE_DITHER_ENABLE;
import static java.awt.RenderingHints.VALUE_FRACTIONALMETRICS_ON;
import static java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC;
import static java.awt.RenderingHints.VALUE_RENDER_QUALITY;
import static java.awt.RenderingHints.VALUE_STROKE_PURE;
import static java.awt.RenderingHints.VALUE_TEXT_ANTIALIAS_ON;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Map;

import Utilities.ImagePool;

public class Painter
{
	private Collection<Paintable> paintables;
	
	private BufferedImage offScrBuffer;
		
	private Map<?, ?> renderingHintMap;
	
	private Camera camera;
	
	private ImagePool imgPool;
	
	public Painter()
	{
		paintables = new LinkedList<Paintable>();
	}
	
	public Painter(Camera camera, ImagePool imgPool)
	{
		this.camera = camera;

		this.imgPool = imgPool;
		
		paintables = new LinkedList<Paintable>();
	
		//setDefaultRenderingHints();
	}
	
	public void paint(Graphics g, Dimension screenDim)
	{
		Graphics2D g2d = (Graphics2D)g;

		camera.setScreenDimension(screenDim);

		GraphicsContext gc = 
				new GraphicsContext(
						g2d, camera.getFocus(),
						screenDim,
						camera.getViewDimension());
	
		camera.transformGraphics(gc.g2d);

		gc.setImagePool(imgPool);
		
		for(Paintable p : paintables)
			p.paint(gc);		
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
