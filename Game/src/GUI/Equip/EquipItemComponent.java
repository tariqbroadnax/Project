package GUI.Equip;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JComponent;

import Graphic.ImagePool;
import Item.EquipItem;

public class EquipItemComponent extends JComponent
{
	private EquipItem item;
	
	public EquipItemComponent()
	{
		setPreferredSize(new Dimension(45, 45));
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		
		int width = getWidth(),
			height = getHeight();
		
		g.drawRect(0, 0, width - 1, height - 1);
		
		if(item != null)
		{
			File file = item.getIconFile();
			
			BufferedImage img = ImagePool.instance.getImage(file);
			
			g.drawImage(img, 0, 0, null);
		}
	}
	
	public void setEquipItem(EquipItem item) {
		this.item = item;
	}
}
