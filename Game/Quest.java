package Game;

import java.util.ArrayList;

import Item.InventoryItem;

public class Quest 
{
	private ArrayList<QuestTask> tasks;
	
	private double expReward;
	private ArrayList<InventoryItem> itemRewards;
		
	public Quest()
	{
		tasks = new ArrayList<QuestTask>();
		
		expReward = 0;
		itemRewards = new ArrayList<InventoryItem>();
	}
	
	public void update()
	{
		for(QuestTask task : tasks)
			task.update();
	}
	
	public void addTasks(QuestTask task)
	{
		tasks.add(task);
	}
	
	public void addItemReward(InventoryItem item)
	{
		itemRewards.add(item);
	}
	
	public boolean checkAndGiveReward()
	{
		return false;
	}
	
	public void setPlayer(Player player)
	{
		for(QuestTask task : tasks)
			task.setPlayer(player);
	}
	
	protected boolean finished()
	{
		for(QuestTask task : tasks)
			if(!task.isFinished())
				return false;
		
		return true;
	}
}
