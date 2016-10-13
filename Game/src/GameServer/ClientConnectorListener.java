package GameServer;

public interface ClientConnectorListener 
{
	public void connectionEstablished(
			ClientConnection connection);
	
	public void connectionBroken(
			ClientConnection connection);
}
