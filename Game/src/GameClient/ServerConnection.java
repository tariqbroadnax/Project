package GameClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ServerConnection 
{
	private Socket socket;
	private ObjectInputStream inStream;
	private ObjectOutputStream outStream;
	
	public ServerConnection(
		Socket socket,
		ObjectInputStream inStream,
		ObjectOutputStream outStream)
	{
		this.socket = socket;
		this.inStream = inStream;
		this.outStream = outStream;
	}
	
	public void sendCommand(ClientCommand command)
	{
		try {
			outStream.writeUnshared(command);
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public Socket getSocket()
	{
		return socket;
	}
	
	public ObjectInputStream getObjectInputStream()
	{
		return inStream;
	}
	
	public ObjectOutputStream getObjectOutputStream()
	{
		return outStream;
	}
}
