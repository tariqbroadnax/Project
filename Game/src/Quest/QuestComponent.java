package Quest;

import java.time.Duration;
import java.util.Collections;
import java.util.HashSet;
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
		quest.setQuester(parent);
			
		if(quest.start() && !quest.isCompleted())
			active.add(quest);
	}
	
	@Override
	public void update(Duration delta) {}

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
