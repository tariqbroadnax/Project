package Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class QuestLog 
implements Serializable
{ 
	private List<Quest> actives,
					    finished;
	
	private Player player;
	
	public QuestLog(Player player)
	{
		actives = new ArrayList<Quest>();
		
		finished = new LinkedList<Quest>();
		
		this.player = player;
	}
	
	public void startQuest(Quest quest)
	{
		actives.add(quest);
		quest.setPlayer(player);
	}
	
	public void update()
	{		
		updateActiveQuests();
		finishValidQuests();
	}
	
	private void updateActiveQuests()
	{
		for(Quest active : actives)
			active.update();
	}
	
	private void finishValidQuests()
	{
		for(int i = 0; i < actives.size();)
		{
			if(actives.get(i)
					  .checkAndGiveReward())
			{
				actives.remove(i);
				finished.add(actives.get(i));
			}
			else
				i++;
		}
	}
}
