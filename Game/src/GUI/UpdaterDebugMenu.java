package GUI;

import Game.GameResources;
import Game.Updater;

public class UpdaterDebugMenu extends DebugMenu
{
	public UpdaterDebugMenu(GameResources resources) 
	{
		super(resources);
	
		Updater updater = resources.updater;
		add(new BoolMenuButton(
				"running", true, b->updater.setRunning(b)));
		add(new SliderMenuButton(
				"target UPS", 60, 10, 30, 120, i -> updater.setTargetFrequency(i)));		
	}
}
