package GameServer;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClientConnection 
{
	private Socket clientSocket;
	
	private ObjectInputStream objInStream;
	
	private ObjectOutputStream objOutStream;
	
	public ClientConnection(
			Socket clientSocket,
			ObjectInputStream objInStream,
			ObjectOutputStream objOutStream)
	{
		this.clientSocket = clientSocket;
		
		this.objInStream = objInStream;
		
		this.objOutStream = objOutStream;
	}
	
	public Socket getClientSocket()
	{
		return clientSocket;
	}
	
	public ObjectInputStream getObjectInputStream()
	{
		return objInStream;
	}
	
	public ObjectOutputStream getObjectOutputStream()
	{
		return objOutStream;
	}
	
}
