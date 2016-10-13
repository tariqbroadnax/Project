package Actions;

import javax.swing.JInternalFrame;

import GameClient.GameClientResources;

public class ToggleItemFrame extends ToggleFrame
{
	
	public ToggleItemFrame(GameClientResources resources) {
		super(resources);
	}

	@Override
	public JInternalFrame frame() 
	{
		return resources.getGUI()
		   .getHUD()
		   .getInventoryFrame();
	}
}
