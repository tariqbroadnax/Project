package GameClient;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import GameServer.ServerUpdate;

public class ServerUpdateBuffer 
	implements ServerUpdateListener
{
	private List<ServerUpdate> updates;
		
	public ServerUpdateBuffer()
	{
		updates = Collections.synchronizedList(new LinkedList<ServerUpdate>());
	}
	
	public Collection<ServerUpdate> getUpdates()
	{
		return Collections.unmodifiableCollection(updates);
	}
	
	public void invokeAll(ClientScene scene)
	{
		synchronized(updates)
		{	
			Iterator<ServerUpdate> updateIter = updates.iterator();
			
			while(updateIter.hasNext())
				updateIter.next().updateScene(scene);
			
			updates.clear();
		}
	}
	
	@Override
	public void updateReceived(ServerUpdate update) 
	{
		updates.add(update);
	}
	
	
}
