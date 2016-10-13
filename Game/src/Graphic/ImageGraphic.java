package Graphic;

import java.awt.Image;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.time.Duration;

public class ImageGraphic extends Graphic
{
	private int imgID;
	
	private Rectangle2D.Double bound;
	
	public ImageGraphic()
	{
		imgID = -1;
	}
	
	public ImageGraphic(int imgID)
	{
		this.imgID = imgID;
	}
	
	public ImageGraphic(ImageGraphic graph)
	{
		imgID = graph.imgID;
	}
	
	@Override
	protected void _paint(GraphicsContext gc) 
	{		
		Image img = gc.getImagePool()
					  .get(imgID);
				
		Point2D.Double screenLoc =
				gc.screenLoc(loc);
		
		
		// README
		// bound created after first paint
		bound = new Rectangle2D.Double(
						loc.x, loc.y,
						img.getWidth(null) * 100.0 / gc.viewDim.width,
						img.getHeight(null) * 100.0 / gc.viewDim.height);
				
		if(gc.showsBound(bound))
			gc.g2d.drawImage(img,
					(int)screenLoc.x,(int)screenLoc.y, null);		
	}
	
	public static AnimatedGraphic animation(Duration delay, int...imageIDs)
	{
		AnimatedGraphic graphic = new AnimatedGraphic();
		
		graphic.setDelay(delay);
		
		for(int i = 0; i < imageIDs.length; i++)
			graphic.addGraphic(new ImageGraphic(imageIDs[i]));
	
		return graphic;
	}
	
	public static AnimatedGraphic animation(Duration delay, int startID, int endID)
	{
		AnimatedGraphic graphic = new AnimatedGraphic();
		
		graphic.setDelay(delay);
		
		for(int i = startID; i <= endID; i++)
			graphic.addGraphic(new ImageGraphic(i));
	
		return graphic;
	}
	
	public void setImageID(int imgID)
	{
		this.imgID = imgID;
	}
	
	public int getImageID()
	{
		return imgID;
	}
	
	public void setLoc(Point2D.Double loc)
	{
		super.setLoc(loc);
		if(bound != null)
		{
			bound.x = loc.x;
			bound.y = loc.y;
		}
	}
	
	@Override
	public Rectangle2D.Double getBound()
	{
		return bound;
	}

	@Override
	protected Graphic _clone() 
	{
		return new ImageGraphic(this);
	}
}
