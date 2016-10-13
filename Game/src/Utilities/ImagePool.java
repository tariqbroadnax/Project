package Utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

import javax.imageio.ImageIO;

public class ImagePool 
{	
	private HashMap<Integer, BufferedImage> imgs;
	
	private int nextID = 0;
		
	private Collection<ImageRecord> imgRecords;
	
	private Collection<ImagePoolListener> listeners;
		
	public ImagePool()
	{		
		imgs = new HashMap<Integer, BufferedImage>();
			
		imgRecords = new LinkedList<ImageRecord>();
		
		listeners = new LinkedList<ImagePoolListener>();
	}

	public void importImage(String fileName, int cols, int rows)
		throws IOException
	{
		BufferedImage img = ImageIO.read(new File(fileName));

		int tileW = img.getWidth() / cols,
			tileH = img.getHeight() / rows;
				
		for(int y = 0; y < img.getHeight(); y+=tileH)
			for(int x = 0; x < img.getWidth(); x+=tileW)
			{
				BufferedImage tile = 
						img.getSubimage(x, y, tileW, tileH);
					
				ImageRecord record = new ImageRecord(
						fileName, nextID,
						x, y, tileW, tileH);
				
				imgRecords.add(record);
				
				put(nextID, tile);
				
				for(ImagePoolListener listener : listeners)
					listener.imageImported(this, nextID);
				
				nextID++;
			}
		
	}
	
	public void put(int imgID, BufferedImage img)
	{		
		imgs.put(imgID, img);
	}
	
	public BufferedImage get(int imgID)
	{
		return imgs.get(imgID);
	}
	
	public void addListener(ImagePoolListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeListener(ImagePoolListener listener)
	{
		listeners.remove(listener);
	}

}
