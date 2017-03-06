package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import Entity.Entity;
import EntityComponent.StatsComponent;
import Stat.Stats;

public class HealthBar extends JPanel
{
	private Entity player;
	
	public HealthBar(Entity player)
	{
		this.player = player;
		
		setPreferredSize(new Dimension(150, 10));
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Stats stats = player.get(StatsComponent.class)
							.getStats();
		
		double maxHealth = stats.getMaxHealth(),
			   health = stats.getHealth(),
			   ratio = health / maxHealth;
		
		
		int height = getHeight(),
			width = getWidth();
		
		g.setColor(Color.red);
		g.fillRect(0, 0, (int)(ratio * width), height);
	
		g.setColor(Color.white);
		g.drawString("HP", 0, 10);
		
		String str = (int)health + "/" + (int)maxHealth;
		int strWidth = g.getFontMetrics()
					    .stringWidth(str);
		
		g.drawString(str, width - strWidth, 10);
	}
}
