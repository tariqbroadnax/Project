package Quest;

import java.util.ArrayList;
import java.util.List;

public class TaskQueue extends Task 
	implements TaskListener
{
	private List<Task> tasks;
	
	private int i;

	public TaskQueue()
	{
		tasks = new ArrayList<Task>();
	}
	
	@Override
	protected void _start() 
	{
		if(tasks.size() == 0)
			complete();
		else
		{
			i = 0;
			
			Task task = tasks.get(i);
			
			task.setTasker(tasker);
			task.start();
		}
	}
	
	public void addTask(Task task)
	{
		tasks.add(task);
		
		task.addTaskListener(this);
	}
	
	public void removeTask(Task task)
	{
		tasks.remove(task);
		
		task.removeTaskListener(this);
	}

	@Override
	protected void _stop() 
	{
		Task task = tasks.get(i);
		
		task.stop();
	}

	@Override
	public void taskCompleted() 
	{
		i++;
		
		if(i == tasks.size())
			complete();
		else
		{
			Task task = tasks.get(i);
			
			task.setTasker(tasker);
			task.start();
		}
	}
}
