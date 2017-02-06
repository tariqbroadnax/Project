package Graphic;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;

import Maths.Dimension2D;

public class Sprite extends Graphic
{
	private static ImagePool pool = new ImagePool();
	
	private File file;
	
	private Dimension2D.Double size;

	private Rectangle tileBound;

	public Sprite()
	{
		this((File)null);
	}
	
	public Sprite(File file)
	{
		setFile(file);
		
		size = new Dimension2D.Double(10, 10);
		
		tileBound = null;
	}
	
	public Sprite(Sprite spr)
	{
		super(spr);
		
		setFile(spr.file);
	
		size = new Dimension2D.Double(
				spr.size.width, spr.size.height);
		
		if(spr.tileBound == null)
			tileBound = null;
		else
			tileBound = new Rectangle(
					spr.tileBound.x, spr.tileBound.y,
					spr.tileBound.width, spr.tileBound.height);
		
	}
	
	@Override
	protected void _paint(GraphicsContext gc) 
	{		
		Graphics2D g2d = (Graphics2D)gc.g2d.create();
		
		BufferedImage img = pool.getImage(file);
				
		if(img == null)
			return;
		
		Rectangle2D.Double scrBound = 
				gc.camera.boundOnScreen2D(loc.x, loc.y, 
										  size.width, size.height);	
				
		int imgW = img.getWidth(), imgH = img.getHeight();
		
		g2d.translate(scrBound.getX() - scrBound.width/2,
					  scrBound.getY() - scrBound.height/2);
		
		g2d.scale(scrBound.width / imgW,
				  scrBound.height / imgH);

		if(tileBound == null)
			g2d.drawImage(img, 0, 0, null);
		else
			g2d.drawImage(img, 0, 0,
						  tileBound.x + img.getWidth(),
						  tileBound.y + img.getHeight(), 
						  tileBound.x, tileBound.y,
						  tileBound.x + tileBound.width,
						  tileBound.y + tileBound.height, null);
		
		g2d.dispose();
	}
	
	public void setFile(File file)
	{
		pool.release(this.file);
		pool.request(file);
		
		this.file = file;
	}
	
	public void setSize(double width, double height) {
		size.width = width; size.height = height;
	}
	
	public void setTileBound(Rectangle tileBound)
	{
		if(tileBound == null)
			this.tileBound = null;
		else
		{
			this.tileBound = new Rectangle();
			this.tileBound.setBounds(tileBound);
		}
	}
	
	public void setTileBound(int x, int y, int w, int h)
	{
		setTileBound(new Rectangle(x,y,w,h));
	}
	
	public File getFile() {
		return file;
	}
	
	public Rectangle getTileBound() 
	{
		if(tileBound == null)
			return null;
		else
		{
			Rectangle tb = new Rectangle();
			
			tb.setBounds(tileBound);
			
			return tb;
		}
	}
	
	@Override
	public Rectangle2D.Double getBound()
	{	
		return new Rectangle2D.Double(
				loc.x - size.width/2,
				loc.y - size.height/2,
				size.width, size.height);
	}

	@Override
	public Object clone() {
		return new Sprite(this);
	}
	
	@Override
	protected void finalize() throws Throwable
	{
		setFile(null);
		
		super.finalize();
	}
}
