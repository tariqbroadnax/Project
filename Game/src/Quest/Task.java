package Quest;

import java.util.ArrayList;
import java.util.List;

import Entity.Entity;

public abstract class Task 
{
	private List<TaskListener> taskLists;
	
	private boolean started, completed;
	
	protected Entity tasker;
	
	public Task()
	{
		taskLists = new ArrayList<TaskListener>();
	
		started = false;
		completed = false;
	}
	
	public void start()
	{
		if(started || tasker == null)
			return;
		
		started = true; completed = false;		
		
		_start();
	}
	
	protected abstract void _start();
	
	public void setTasker(Entity tasker) 
	{
		if(started)
			stop();
			
		this.tasker = tasker;
	}
	
	public void stop()
	{
		started = false; completed = false;
	
		_stop();
	}
	
	protected abstract void _stop();
	
	protected void complete()
	{
		completed = true;
		
		for(TaskListener list : taskLists)
			list.taskCompleted();
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	public void addTaskListener(TaskListener list) {
		taskLists.add(list);
	}
	
	public void removeTaskListener(TaskListener list) {
		taskLists.remove(list);
	}
}
