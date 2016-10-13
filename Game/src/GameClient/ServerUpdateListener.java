package GameClient;

import GameServer.ServerUpdate;

public interface ServerUpdateListener 
{
	public void updateReceived(ServerUpdate update);
}
