package Actions;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;

public class ActionBuffer 
{
	protected Collection<Runnable> actions;
	
	public ActionBuffer()
	{
		actions = Collections.synchronizedCollection(
				new LinkedList<Runnable>());
	}
	
	public void schedule(Runnable action)
	{
		actions.add(action);
	}
	
	public void invokeAll()
	{
		synchronized(actions)
		{
			Iterator<Runnable> iter = 
					actions.iterator();
			
			while(iter.hasNext())
				iter.next().run();
		}
		
		actions.clear();
	}
}
