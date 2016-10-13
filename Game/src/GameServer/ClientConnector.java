package GameServer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class ClientConnector
{
	private boolean connecting;
	
	private Thread connector, connectionChecker;
	
	private ServerSocket serverSocket;
	
	private Collection<ClientConnection> connections;
	
	private Collection<ClientConnectorListener> listeners;
					
	public ClientConnector(
			ServerSocket serverSocket)
	{					
		this.serverSocket = serverSocket;
		
		connections = 
		Collections.synchronizedList(
				new LinkedList<ClientConnection>());
		
		listeners = 
		Collections.synchronizedList(
				new LinkedList<ClientConnectorListener>());
		
		connector = new Thread(
				() -> connectToClients());
		
		connectionChecker = new Thread(
				() -> checkForDisconnects());
		
		connecting = false;
	}
		
	private void connectToClients()
	{
		while(connecting)
		{
			try {
				connectToClient();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void connectToClient()
		throws IOException
	{
		Socket clientSocket = 
				serverSocket.accept();
		
		ObjectOutputStream objOutStream =
				new ObjectOutputStream(
						clientSocket
						.getOutputStream());
		
		ObjectInputStream objInStream =
				new ObjectInputStream(
						clientSocket
						.getInputStream());
		
		ClientConnection connection = 
				new ClientConnection(
						clientSocket,
						objInStream,
						objOutStream);	
			
		for(ClientConnectorListener listener :
			listeners)
			listener.connectionEstablished(
					connection);
		
		connections.add(connection);
	}
	
	private void checkForDisconnects()
	{		
		while(connecting ||
			  !connections.isEmpty())
		{	
			try {
				checkForDisconnect();
			} catch(InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void checkForDisconnect()
		throws InterruptedException
	{
		Thread.sleep(1000);
		
		if(connections.size() == 0) return;
		
		LinkedList<ClientConnection> disconnects =
				new LinkedList<ClientConnection>();
		
		synchronized(connections)
		{
			Iterator<ClientConnection> connIter = connections.iterator();
			
			ClientConnection conn = connIter.next();
			
			if(conn.getClientSocket()
				   .isClosed())
				disconnects.add(conn);
		}			
	
		connections.removeAll(disconnects);
			
		for(ClientConnection connection : disconnects)
			for(ClientConnectorListener listener : listeners)
				listener.connectionBroken(connection);
	}
	
	public void closeConnections() 
			throws IOException
	{
		for(ClientConnection connection : connections)
			connection.getClientSocket()
					  .close();
	}

	public void addServerConnectorListener(
			ClientConnectorListener listener)
	{
		listeners.add(listener);
	}

	public void removeServerConnectorListener(
			ClientConnectorListener listener)
	{
		listeners.remove(listener);
	}
	
	public void startConnecting()
	{	
		connecting = true;

		connector.start();
		
		connectionChecker.start();
	}
	
	public boolean isConnecting()
	{
		return connecting;
	}
	
	public void stop()
	{
		connecting = false;
		
		try {
			closeConnections();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Collection<ClientConnection> getConnections()
	{
		return connections;
	}
}


