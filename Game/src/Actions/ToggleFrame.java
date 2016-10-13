package Actions;

import javax.swing.JInternalFrame;

import GameClient.GameClientResources;

public abstract class ToggleFrame extends ClientAction
{	
	public ToggleFrame(GameClientResources resources) 
	{
		super(resources);
	}

	public boolean isSynced()
	{
		return false;
	}
	
	public abstract JInternalFrame frame();
	
	@Override
	public void invokeOnClient()
	{
		JInternalFrame frame = frame();
		
		frame.setVisible(!frame.isVisible());
	}
}
