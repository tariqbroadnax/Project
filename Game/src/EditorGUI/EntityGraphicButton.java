package EditorGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JButton;

import EntityComponent.GraphicsComponent;
import Game.Entity;

public class EntityGraphicButton extends JButton
{
	private Entity entity;
	
	public EntityGraphicButton(Entity entity)
	{
		this.entity = entity;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(entity.contains(GraphicsComponent.class))
		{
			Graphics2D g2d = (Graphics2D)g;
		
			GraphicsComponent comp = 
					entity.get(GraphicsComponent.class);
		
			Point paintLoc = new Point();
			Dimension proj = getSize();
			
			comp.getGraphic()
				.paint(g2d, paintLoc, proj);
		}
		else
			drawTransparencyGrid(g);
	}
	
	private void drawTransparencyGrid(Graphics g)
	{		
		Color color = Color.gray;
		
		for(int i = 0; i < 16; i++)
		{
			int c = i % 4,
				r = i / 4;
			
			if(i % 4 != 0)
			{
				color = color == Color.gray ?
						Color.white : Color.gray;
			}
			
			g.setColor(color);
			
			g.fillRect(c * 10, r * 10, 10, 10);
		}
	}
}
