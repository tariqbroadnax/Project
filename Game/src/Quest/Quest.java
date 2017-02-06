package Quest;

import java.util.ArrayList;
import java.util.List;

import Entity.Entity;

public class Quest implements TaskListener
{
	private String name, description;
	
	private List<Requirement> reqs;
	private List<Task> tasks;
	private List<Reward> rewards;
	
	private boolean completed;
	
	private Entity quester;
	
	public Quest()
	{
		reqs = new ArrayList<Requirement>();
		tasks = new ArrayList<Task>();
		rewards = new ArrayList<Reward>();
		
		completed = false;
	}
	
	public boolean canBeStarted() 
	{
		for(Requirement req : reqs)
			if(!req.isSatisfied(quester))
				return false;
		return true;
	}
	
	public void start()
	{
		// DEBUG --
		System.out.println("Quest started: " + name);
		// --------
		
		for(Task task : tasks)
			task.start(quester);
		
		checkAndComplete();
	}
	
	protected void checkAndComplete()
	{
		for(Task task : tasks)
			if(!task.isCompleted())
				return;
		
		// DEBUG --
		System.out.println("Quest completed: " + name);
		// --------
		
		completed = true;
		
		for(Reward reward : rewards)
			reward.give(quester);
	}
	
	public void setQuester(Entity quester) {
		this.quester = quester;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public boolean isCompleted() {
		return completed;
	}
	
	public void addRequirement(Requirement req) {
		reqs.add(req);
	}
	
	public void removeRequirement(Requirement req) {
		reqs.remove(req);
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
	
	public void addReward(Reward reward) {
		rewards.add(reward);
	}
	
	public void removeReward(Reward reward) {
		rewards.remove(reward);
	}

	@Override
	public void taskCompleted() {
		checkAndComplete();
	}
}