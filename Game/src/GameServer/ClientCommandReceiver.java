package GameServer;

import java.io.IOException;
import java.util.LinkedList;

import GameClient.ClientCommand;

public class ClientCommandReceiver 
	implements Runnable
{
	private ClientConnection connection;
	
	private boolean receiving;
	
	private Thread thread;
	
	private LinkedList<ClientCommandListener> listeners;
	
	public ClientCommandReceiver(ClientConnection connection)
	{
		this.connection = connection;
		
		receiving = false;
		
		thread = new Thread(this);
		
		listeners = new LinkedList<ClientCommandListener>();
	}
	
	@Override
	public void run() 
	{
		while(receiving)
		{
			try {
				recieveCommand();
			} catch(IOException | ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void recieveCommand()
		throws IOException, ClassNotFoundException
	{
		ClientCommand command =
		(ClientCommand)
				connection.getObjectInputStream()
						  .readObject();
		
		for(ClientCommandListener listener :
			listeners)
		{
			listener.commandRecieved(connection, command);
		}
	}
	
	public void start()
	{
		receiving = true;
		
		thread.start();
	}
	
	public void stop()
	{
		receiving = false;
	}
	
	public void addClientCommandListener(
			ClientCommandListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeClientCommandListener(
			ClientCommandListener listener)
	{
		listeners.remove(listener);
	}

}
