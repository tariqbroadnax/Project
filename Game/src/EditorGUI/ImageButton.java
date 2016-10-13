package EditorGUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;

public class ImageButton extends JButton
{			
	private GUIResources resources;
	
	private int imgID;
	
	private boolean selected = false;
	
	public ImageButton(GUIResources resources, int imgID)
	{
		this.resources = resources;
		
		this.imgID = imgID;
		
		BufferedImage img = 
				resources.getImagePool()
						 .get(imgID);
		
		Image scaledImg = 
				img.getScaledInstance(
						40, 40, 
						BufferedImage.SCALE_FAST);
		
		setIcon(new ImageIcon(scaledImg));
		
		setBorder(BorderFactory.createEmptyBorder());
	
		addActionListener(e -> selectTile());
	}
	
	private void selectTile()
	{
		ImageID imageID = new ImageID(this.imgID);
		
		if(imageID.equals(resources.getSelectedObject()))
			resources.setSelectedObj(null);
		else
			resources.setSelectedObj(imageID);
		
		selected = !selected;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
		
		Color color = Color.black;
		
		g2d.setStroke(new BasicStroke(3));
		
		if(selected)
			g.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}
}
