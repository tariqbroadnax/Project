package EditorGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

public class ImageViewer extends JPanel
{	
	public final int minReqWidth = 100, 
					 minReqHeight = 100;
	
	public final Dimension DEFAULT_PREF_SIZE = 
			 new Dimension(0, 0);
	
	private Dimension imgScreenSize;
	
	private BufferedImage img;

	public ImageViewer()
	{
		this(null);
	}
	
	public ImageViewer(BufferedImage img)
	{
		this.img = img;
				
		setBackground(Color.white);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		drawImage(g);
	}
	
	protected void drawImage(Graphics g)
	{	
		if(img != null)
			g.drawImage(img, 0, 0, imgScreenSize.width, 
							   	   imgScreenSize.height, null);
	}
	
	private Dimension imgSizeOnScreen()
	{		
		int imgWidth = img.getWidth(), 
			imgHeight = img.getHeight();
		
		Dimension imgSize = new Dimension(
				imgWidth, imgHeight);
		
		AspectRatio ratio = new AspectRatio(imgSize);
		
		return ratio.smallestValidDimension(
				imgSize, minReqWidth, minReqHeight);
	}
	
	public void setImage(BufferedImage img)
	{
		this.img = img;
		
		if(img == null)
		{
			setPreferredSize(DEFAULT_PREF_SIZE);
		}
		else
		{
			imgScreenSize = imgSizeOnScreen();
			setPreferredSize(imgScreenSize);
		}
		
		revalidate();
		repaint();
	}
	
	
	public BufferedImage getImage()
	{
		return img;
	}
	
	public Dimension getImageScreenSize()
	{
		return imgScreenSize;
	}
}
