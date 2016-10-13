package Actions;

import GameClient.GameClientResources;

public class HideDialogueFrame extends GameAction
{
	public HideDialogueFrame(GameClientResources resources) 
	{
		super(resources);
	}

	@Override
	public void executeOnClient() 
	{
		resources.getGUI()
				 .getHUD()
				 .getDialogueFrame()
				 .setVisible(false);
	}

}
