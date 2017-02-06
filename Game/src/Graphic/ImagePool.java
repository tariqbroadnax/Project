package Graphic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImagePool 
{
	private Map<File, Node> map;
	
	public ImagePool()
	{
		map = new HashMap<File, Node>();
	}
	
	public void request(File file)
	{
		if(file == null)
			return;
		
		if(!map.containsKey(file))
		{
			Node node = new Node();
			
			try {
				node.img = ImageIO.read(file);
			} catch(IOException e) {}
			
			map.put(file, node);
		}
		
		Node node = map.get(file);
		
		node.requests++;
	}
	
	public void release(File file)
	{
		if(file == null || !map.containsKey(file))
			return;
	
		Node node = map.get(file);
		
		node.requests--;
	
		if(node.requests == 0)
			map.remove(file);
	}
	
	public BufferedImage getImage(File file) 
	{
		if(file != null && map.containsKey(file))
			return map.get(file).img;
		return null;
	}
	
	private class Node
	{
		BufferedImage img;
		int requests = 0;
	}
}
