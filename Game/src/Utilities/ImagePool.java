package Utilities;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.RenderingHints.Key;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import Tileset.Tile;
import Tileset.Tileset;

public class ImagePool 
{	
	private Map<File, Integer> imgIDs;
	private Map<Tile, Integer> tileIDs;
	private Map<Integer, BufferedImage> imgs;
	
	private int nextID;
	
	private RenderingHints renderingHints;
				
	public ImagePool()
	{				
		imgIDs = new HashMap<File, Integer>();
		tileIDs = new HashMap<Tile, Integer>();
		imgs = new HashMap<Integer, BufferedImage>();	
	
		nextID = 0;		
		
		renderingHints = new RenderingHints(null);
	}
	
	public void importImage(File file)
		throws IOException
	{
		dispose(file);
		
		BufferedImage img = ImageIO.read(file);
		
		int width = img.getWidth(),
			height = img.getHeight();
		
		imgIDs.put(file, nextID);
		importImage(img, width, height);
	}
	
	public void importImage(
			File file, int width, int height)
		throws IOException
	{
		dispose(file);
		
		BufferedImage img = ImageIO.read(file);
		
		imgIDs.put(file, nextID);
		importImage(img, width, height);
	}
	
	private void importImage(
			BufferedImage img, int width, int height)
	{
		int imgW = img.getWidth(),
			imgH = img.getHeight();
		
		if(imgW == width && imgH == height)
			imgs.put(nextID++, img);
		else
		{
			BufferedImage scaledImage =
					scaledImage(img, width, height);
			
			imgs.put(nextID++, scaledImage);
		}
	}
	
	private BufferedImage scaledImage(
			BufferedImage img, int width, int height)
	{
		BufferedImage scaledImage = 
				new BufferedImage(
						width, height, 
						BufferedImage.TYPE_INT_ARGB);
		
		Graphics2D g2d = (Graphics2D)
				scaledImage.getGraphics();
		
		g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
							 RenderingHints.VALUE_INTERPOLATION_BICUBIC);
		
		g2d.setRenderingHint(RenderingHints.KEY_RENDERING,
							 RenderingHints.VALUE_RENDER_QUALITY);

		
		//g2d.setRenderingHints(renderingHints);
		
		g2d.drawImage(img, 0, 0, width, height, null);
		
		return scaledImage;
	}
	
	public void importTileset(
			Tileset ts, int width, int height) throws IOException
	{
		dispose(ts);

		File file = ts.getFile();
		BufferedImage img = ImageIO.read(file);
		
		importTileset(ts, img, width, height);
	}
		
	public void importTileset(
			Tileset ts, int width, int height,
			BufferedImage img)
	{
		dispose(ts);
		importTileset(ts, img, width, height);
	}
	
	private void importTileset(
			Tileset ts, BufferedImage img, 
			int width, int height)
	{
		int imgW = img.getWidth(), 
			imgH = img.getHeight();
			
		if(imgW == width && imgH == height)
			importTileset(ts, img);
		else
		{
			BufferedImage scaledImg = 
					scaledImage(img, width, height);
			
			importTileset(ts, scaledImg);
		}
	}
	
	public void importTileset(
			Tileset ts, BufferedImage img)
	{		
		dispose(ts);
		
		int rows = ts.getRows(),
			cols = ts.getCols();
			
		int imgW = img.getWidth(),
			imgH = img.getHeight(),
			
			tileW = imgW / cols,
			tileH = imgH / rows;
		
		for(int row = 0; row < rows; row++)
			for(int col = 0; col < cols; col++)
			{
				Tile tile = ts.get(row, col);
				
				int x = col * tileW,
					y = row * tileH;
				
				BufferedImage tileImg = 
						img.getSubimage(x, y, tileW, tileH);
				
				tileIDs.put(tile, nextID);
				imgs.put(nextID++, tileImg);
			}
	}
	
	public void importTileset(Tileset ts) throws IOException
	{
		dispose(ts);
		
		File file = ts.getFile();
		BufferedImage img = ImageIO.read(file);
		
		importTileset(ts, img);
	}
	
	public void dispose(Tileset ts)
	{
		int rows = ts.getRows(),
			cols = ts.getCols();
		
		for(int row = 0; row < rows; row++)
			for(int col = 0; col < cols; col++)
			{
				Tile tile = ts.get(row, col);
				
				if(tileIDs.containsKey(tile))
				{
					int id = tileIDs.get(tile);
					
					tileIDs.remove(tile);
					imgs.remove(id);
				}
			}
	}
	
	public void dispose(File file)
	{
		if(imgIDs.containsKey(file))
		{
			int id = imgIDs.get(file);
			imgIDs.remove(file);
			imgs.remove(id);
		}
	}
	
	public void setRenderingHints(
			Key key, Object value)
	{
		renderingHints.put(key, value);
	}

	public BufferedImage getImage(String fileName)
	{
		File file = new File(fileName);
		
		return getImage(file);
	}
	
	public BufferedImage getImage(File file)
	{
		int id = imgIDs.get(file);

		return imgs.get(id);
	}
	
	public BufferedImage getImage(String fileName, int row, int col)
	{
		File file = new File(fileName);
		
		return getImage(file, row, col);
	}

	public BufferedImage getImage(File file, int row, int col)
	{
		Tile tile = new Tile(file, row, col);
		
		return getImage(tile);
	}
	
	public BufferedImage getImage(Tile tile)
	{
		int id = tileIDs.get(tile);
		
		return imgs.get(id);
	}
	
	public List<BufferedImage> getTiles(Tileset ts)
	{
		int rows = ts.getRows(),
			cols = ts.getCols();
			
		ArrayList<BufferedImage> tiles =
				new ArrayList<BufferedImage>(cols * rows);

		for(int row = 0; row < rows; row++)
			for(int col = 0; col < cols; col++)
			{
				Tile tile = ts.get(row, col);
				BufferedImage img = getImage(tile);
				
				tiles.add(img);
			}
		
		return tiles;
	}
}