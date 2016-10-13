package Actions;

import java.awt.event.ActionEvent;


import javax.swing.AbstractAction;

import GUI.GUI;
import GameClient.ClientScene;
import GameClient.GameClientResources;
import GameClient.PlayerRecord;

public abstract class ClientAction extends GameAction
{
	public ClientAction(
		   GameClientResources resources) 
	{
		super(resources);
	}

	public abstract boolean isSynced();
	
	public void checkAndInvoke()
	{
		if(isSynced())
			resources.getActionBuffer()
					 .addAction(this);
		else
			invokeOnClient();
	}
	 
	public abstract void invokeOnClient();
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		checkAndInvoke();
	}
}
