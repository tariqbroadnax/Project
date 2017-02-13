package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Editor.ComponentListener;

public class GPanel extends JPanel
	implements ComponentListener
{	
	private BufferedImage buffer;
	
	public GPanel()
	{	
		setLayout(new BorderLayout());
		addComponentListener(this);
	}
	
	public Graphics getBufferGraphics() 
	{
		Dimension size = getSize();
		
		if(size.width == 0 || size.height == 0)
			return null;
		
		if(buffer == null || 
		   !(buffer.getWidth() == size.width &&
		     buffer.getHeight() == size.height))
		{
			buffer = new BufferedImage(
					size.width, size.height, BufferedImage.TYPE_INT_ARGB);
		
		}
		return buffer.getGraphics();
	}
	
	public void showBuffer()
	{
		Graphics g = getGraphics();
		
		if(g != null) 
			g.drawImage(buffer, 0, 0, null);
	}
	
}
