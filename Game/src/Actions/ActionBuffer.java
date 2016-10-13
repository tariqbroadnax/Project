package Actions;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class ActionBuffer 
{
	protected Collection<SyncGameAction> actions;
	
	public ActionBuffer()
	{
		actions = Collections.synchronizedCollection(
				new LinkedList<SyncGameAction>());
	}
	
	public void addAction(SyncGameAction action)
	{
		actions.add(action);
	}
	
	public void invokeAll()
	{
		synchronized(actions)
		{
			Iterator<SyncGameAction> iter = 
					actions.iterator();
			
			while(iter.hasNext())
				iter.next().invoke();
		}
		
		actions.clear();
	}
}
