package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import Entity.Entity;
import EntityComponent.StatsComponent;
import Stat.Stats;

public class ManaBar extends JPanel
{
	private Entity player;
	
	public ManaBar(Entity player)
	{
		this.player = player;
		
		setPreferredSize(new Dimension(150, 10));
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Stats stats = player.get(StatsComponent.class)
							.getStats();
		
		double maxMana = stats.getMaxMana(),
			   mana = stats.getMana(),
			   ratio = mana / maxMana;
		
		
		int height = getHeight(),
			width = getWidth();
		
		g.setColor(Color.blue);
		g.fillRect(0, 0, (int)(ratio * width), height);
	
		g.setColor(Color.white);
		g.drawString("MP", 0, 10);
		
		String str = (int)mana + "/" + (int)maxMana;
		int strWidth = g.getFontMetrics()
					    .stringWidth(str);
		
		g.drawString(str, width - strWidth, 10);
	}
}

