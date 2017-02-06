package Quest;

import java.util.ArrayList;
import java.util.List;

import Entity.Entity;

public abstract class Task 
{
	private List<TaskListener> taskLists;
	
	private boolean completed;
	
	public Task()
	{
		taskLists = new ArrayList<TaskListener>();
	
		completed = false;
	}
	
	public abstract void start(Entity ent);
	
	protected void taskCompleted()
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
