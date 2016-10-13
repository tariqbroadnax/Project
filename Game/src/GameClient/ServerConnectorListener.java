package GameClient;

public interface ServerConnectorListener 
{
	public void connectionEstablished(
			ServerConnection connection);
	
	public void connectionBroken(
			ServerConnection connection);
}
