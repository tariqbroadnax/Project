package Utilities;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import Game.Updatable;

public class Scheduler implements Updatable
{	
	private List<Task> tasks;
	
	public Scheduler()
	{
		tasks = new ArrayList<Task>();
	}
	
	@Override
	public void update(Duration delta) 
	{
		for(Task task : tasks)
		{
			task.delay -= delta.toMillis();
			
			if(task.delay < 0)
				task.runnable.run();
		}
		
		tasks.removeIf(task -> task.delay < 0);
	}
	
	public void schedule(Runnable runnable, long delay)
	{
		Task task = new Task();
		
		task.runnable = runnable;
		task.delay = delay;
		
		tasks.add(task);
	}
	
	private class Task
	{
		Runnable runnable;
		long delay;
	}
}
