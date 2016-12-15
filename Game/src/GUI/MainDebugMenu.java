package GUI;

import Game.GameResources;

public class MainDebugMenu extends DebugMenu
{
	public MainDebugMenu(GameResources resources)
	{
		super(resources);
		
		UpdaterDebugMenu menu = new UpdaterDebugMenu(resources);
		
		add(new NavMenuButton("updater...", menu));
	}
}
