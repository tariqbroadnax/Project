package Actions;

import java.awt.event.ActionEvent;

import GameClient.ClientCommand;
import GameClient.GameClientResources;

public abstract class ServerAction extends ClientAction
	implements ClientCommand
{
	public ServerAction(
			GameClientResources resources) 
	{
		super(resources);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		super.actionPerformed(e);
		
		resources.getClientNetwork()
				 .getServerConnector()
				 .getServerConnection()
				 .sendCommand(this);
	}
}
