package GameServer;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import GameClient.ClientCommand;

public class ClientCommandBuffer
	implements ClientCommandListener
{	
	private Map<ClientConnection,
				LinkedList<ClientCommand>> buffer;
				
	public ClientCommandBuffer()
	{
		buffer = Collections.synchronizedMap(
				new HashMap<ClientConnection, 
							LinkedList<ClientCommand>>());
	}
	
	public Collection<ClientCommand> getRequestOf(
			ClientConnection connection)
	{
		if(buffer.get(connection) == null)
			return new LinkedList<ClientCommand>();
		else
			return Collections.unmodifiableCollection(
					buffer.get(connection));
	}
	
	public Collection<ClientCommand> getAllRequest()
	{
		LinkedList<ClientCommand> requests =
				new LinkedList<ClientCommand>();
		
		for(ClientConnection conn : buffer.keySet())
			requests.addAll(buffer.get(conn));
		
		return Collections.unmodifiableCollection(requests);
	}
	
	public void flush()
	{
		buffer.clear();
	}

	@Override
	public void commandRecieved(
			ClientConnection src, 
			ClientCommand command) 
	{
		if(!buffer.containsKey(src))
			buffer.put(src, new LinkedList<ClientCommand>());
		
		buffer.get(src)
			  .add(command);
	}
}
