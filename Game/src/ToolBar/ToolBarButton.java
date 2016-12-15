package ToolBar;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public abstract class ToolBarButton extends JButton
{
	public ToolBarButton()
	{
		String iconFileName = iconFileName();
		
		try {
			BufferedImage img = 
					ImageIO.read(new File(iconFileName));
			
			ImageIcon icon = new ImageIcon(img);
		
			setIcon(icon);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	protected abstract String iconFileName();
}
