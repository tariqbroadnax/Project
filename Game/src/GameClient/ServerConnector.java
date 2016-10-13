package GameClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JOptionPane;

public class ServerConnector 
{
	private String hostName = "LAPTOP-RQ0KLDJF";

	private int portNumber = 3000;
	
	private boolean connected;

	private ServerConnection connection;
	
	private Collection<ServerConnectorListener> listeners;
	
	public ServerConnector()
	{
		connected = false;
		
		connection = null;
		
		listeners = new LinkedList<ServerConnectorListener>();
	}
	
	public void connectToServer()
	{
		if(connected) return;
		
		try {
			Socket socket = 
					new Socket(hostName, portNumber);			

			ObjectInputStream inStream = new ObjectInputStream(
					socket.getInputStream());
			ObjectOutputStream outStream = new ObjectOutputStream(
					socket.getOutputStream());

			connection = new ServerConnection(socket, inStream, outStream);
			
			for(ServerConnectorListener listener : listeners)
				listener.connectionEstablished(connection);
			
			connected = true;
			
		} catch (IOException e) {
			e.printStackTrace();
			handleException(e);
		}
	}
	
	public void disconnectFromServer()
	{
		if(!connected) return;
	}
	
	private void handleException(IOException e)
	{
		JOptionPane.showMessageDialog(null,
				"Cannot Connect to Server");
	}
	
	public void addServerConnectorListener(
			ServerConnectorListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeServerConnectorListener(
			ServerConnectorListener listener)
	{
		listeners.remove(listener);
	}
	
	public boolean isConnected()
	{
		return connected;
	}
	
	public ServerConnection getServerConnection()
	{
		return connection;
	}
}
