package GUI;

import javax.swing.JPanel;

import EntityComponent.StatsComponent;
import Game.GameResources;
import Stat.Stats;

public class StatPanel extends JPanel
{
	private Stats stats;
	
	public StatPanel(GameResources resources)
	{
		stats = resources
				.getPlayer()
				.get(StatsComponent.class)
				.getStats();
				
	}
}
