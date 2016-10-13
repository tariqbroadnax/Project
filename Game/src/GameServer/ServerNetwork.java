package GameServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.HashMap;

public class ServerNetwork
	implements ClientConnectorListener
{
	private ServerSocket serverSocket;
	
	private ClientConnector connector;
	
	private ClientCommandBuffer commandBuffer;
			
	private HashMap<ClientConnection,
					ClientCommandReceiver> commandReceivers;
		
	public ServerNetwork()
	{		
		serverSocket = createServerSocket();
		
		connector = new ClientConnector(serverSocket);		
		
		commandBuffer = new ClientCommandBuffer();
		
		commandReceivers = new HashMap<ClientConnection,
									   ClientCommandReceiver>();
				
		connector.startConnecting();
	}
	
	protected void importConnection(ClientConnection connection)
	{
		ClientCommandReceiver commandReceiver =
				new ClientCommandReceiver(connection);
		
		commandReceiver.addClientCommandListener(
				commandBuffer);
				
		commandReceivers.put(connection, commandReceiver);
		
		commandReceiver.start();
	}
	
	protected void exportConnection(ClientConnection connection)
	{			
		commandReceivers.get(connection)
						.stop();
		
		commandReceivers.remove(connection);
	}
	
	public ClientConnector getClientConnector()
	{
		return connector;
	}
	
	private ServerSocket createServerSocket()
	{
		try {
			return new ServerSocket(3000);
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}	
		return null;
	}
	
	public ClientCommandBuffer getClientCommandBuffer()
	{
		return commandBuffer;
	}
	
	@Override
	public void connectionEstablished(ClientConnection connection) 
	{
		importConnection(connection);
	}

	@Override
	public void connectionBroken(ClientConnection connection) 
	{
		exportConnection(connection);
	}
}
