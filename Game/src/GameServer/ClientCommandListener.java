package GameServer;

import GameClient.ClientCommand;

public interface ClientCommandListener 
{
	public void commandRecieved(
			ClientConnection src,
			ClientCommand command);
}
