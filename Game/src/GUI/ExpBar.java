package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import Entity.Entity;
import EntityComponent.StatsComponent;
import Stat.Stats;

public class ExpBar extends JPanel
{
	private Entity player;
	
	public ExpBar(Entity player)
	{
		this.player = player;
		
		setPreferredSize(new Dimension(150, 10));
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Stats stats = player.get(StatsComponent.class)
							.getStats();
		
		int nextExp = stats.getExpNextLvl(),
			exp = stats.getExp();
		double ratio = exp * 1.0 / nextExp;
		
		
		int height = getHeight(),
			width = getWidth();
		
		g.setColor(Color.yellow);
		g.fillRect(0, 0, (int)(ratio * width), height);
	
		int level = stats.getLevel();
		
		g.setColor(Color.white);
		g.drawString("LV " + level , 0, 10);
		
		String str = ratio * 100 + "%";
		
		int strWidth = g.getFontMetrics()
					    .stringWidth(str);
		
		g.drawString(str, width - strWidth, 10);
	}
}
