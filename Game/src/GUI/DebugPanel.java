package GUI;

import java.awt.Graphics;

import javax.swing.JPanel;

import Game.Resources;

public class DebugPanel extends JPanel
{
	private boolean debugTextEnabled;
	
	private Resources resources;
	
	public DebugPanel(Resources resources)
	{
		this.resources = resources;
		
		debugTextEnabled = false;
		
		setOpaque(false);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(!debugTextEnabled)
			return;
		
		double UPS = resources
				.getUpdater()
	  			.getActualFrequency();
		
		g.drawString("UPS: " + UPS, 0, 50);		
	}
	
	public void setDebugTextEnabled(boolean debugTextEnabled)
	{
		this.debugTextEnabled = debugTextEnabled;
	}
}
