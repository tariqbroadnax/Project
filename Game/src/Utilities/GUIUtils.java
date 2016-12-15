package Utilities;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class GUIUtils {

	public static ImageIcon ImageIcon(
			String imgFileName) 
	{
		File imgFile = new File(imgFileName);
		
		try {
			BufferedImage img = ImageIO.read(imgFile);
			ImageIcon icon = new ImageIcon(img);
			
			return icon;
			
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return null;
		}
	}
}
