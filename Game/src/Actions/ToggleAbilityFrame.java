package Actions;

import javax.swing.JInternalFrame;

import GameClient.GameClientResources;

public class ToggleAbilityFrame extends ToggleFrame
{

	public ToggleAbilityFrame(GameClientResources resources) 
	{
		super(resources);
	}

	@Override
	public JInternalFrame frame() 
	{
		return resources.getGUI()
						.getHUD()
						.getAbilityFrame();
	}

}
