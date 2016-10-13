package GameClient;

import java.io.IOException;

public class ClientNetwork 
	implements ServerConnectorListener
{
	private ServerConnector connector;
	
	private ServerUpdateReceiver receiver;
	
	private ServerUpdateBuffer buffer;
	
	private PlayerRecord playerRecord;
	
	public ClientNetwork(PlayerRecord playerRecord)
	{
		this.playerRecord = playerRecord;
		
		connector = new ServerConnector();
		
		buffer = new ServerUpdateBuffer();
		
		connector.addServerConnectorListener(this);
	}
	
	public void start()
	{
		connector.connectToServer();
	}

	@Override
	public void connectionEstablished(ServerConnection connection) 
	{
		if(receiver != null) 
			receiver.stop();
	
		try {
			int entityID = 
			(Integer)
					connection.getObjectInputStream()
							  .readObject();			
			
			playerRecord.setEntityID(entityID);
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
		}
		
		receiver = new ServerUpdateReceiver(connection);
		
		receiver.addServerUpdateListener(buffer);
		
		receiver.start();
	}

	@Override
	public void connectionBroken(ServerConnection connection) 
	{
		receiver.stop();
	}
	
	public ServerConnector getServerConnector()
	{
		return connector;
	}
	
	public ServerUpdateBuffer getServerUpdateBuffer()
	{
		return buffer;
	}
	
	public ServerUpdateReceiver getServerUpdateReceiver()
	{
		return receiver;
	}
}
