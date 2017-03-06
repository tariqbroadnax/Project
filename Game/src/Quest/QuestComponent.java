package Quest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import EntityComponent.EntityComponent;

public class QuestComponent extends EntityComponent
{
	public Set<Quest> active, completed;
	
	public QuestComponent()
	{
		active = new HashSet<Quest>();
		completed = new HashSet<Quest>();
	}
	
	public QuestComponent(QuestComponent comp)
	{
		this();
	}
	
	public void startQuest(Quest quest)
	{			
		boolean canBeStarted = true;
		
		for(Requirement req : quest.getRequirements())
			if(!req.isSatisfied(parent))
				canBeStarted = false;
		
		if(canBeStarted)
		{
			active.add(quest);
		
			for(Task task : quest.getTasks())
			{
				task.setTasker(parent);
				task.start();
			}
		}
	}
	
	@Override
	public void update(Duration delta) 
	{
		List<Quest> remove = new ArrayList<Quest>();
		
		for(Quest quest : active)
		{
			boolean finished = true;
			
			for(Task task : quest.getTasks())
				if(!task.isFinished())
					finished = false;
			
			if(finished)
			{
				for(Task task : quest.getTasks())
					task.stop();
				
				completed.add(quest);
				remove.add(quest);
			}
		}
		
		active.removeAll(remove);
	}

	public Set<Quest> getActiveQuests() {
		return Collections.unmodifiableSet(active);
	}
	
	public Set<Quest> getCompletedQuests() {
		return Collections.unmodifiableSet(completed);
	}

	@Override
	protected EntityComponent _clone() {
		return new QuestComponent(this);
	}
}
