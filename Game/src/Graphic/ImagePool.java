package Graphic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

public class ImagePool 
{
	public static final ImagePool instance = new ImagePool();
	
	private Map<String, Node> map;
	
	private ImagePool()
	{
		map = new HashMap<String, Node>();
	}
	
	public void request(File file)
	{
		if(file == null)
			return;
		
		String path = file.getAbsolutePath();
		
		if(!map.containsKey(path))
		{
			Node node = new Node();
			
			try {
				node.img = ImageIO.read(file);
			} catch(IOException e) {}
			
			map.put(path, node);
		}
		
		Node node = map.get(path);
		
		node.requests++;
	}
	
	public void release(File file)
	{
		if(file == null) return;
		
		String path = file.getAbsolutePath();

		if(!map.containsKey(path))
			return;
	
		Node node = map.get(path);
		
		node.requests--;

		if(node.requests == 0)
		{
			map.remove(file);
		}
	}
	
	public BufferedImage getImage(File file) 
	{
		String path = file.getAbsolutePath();
		
		if(map.containsKey(path))
			return map.get(path).img;
		
		return null;
	}
	
	private class Node
	{
		BufferedImage img;
		int requests = 0;
	}
}
