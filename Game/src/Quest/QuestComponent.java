package Quest;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import EntityComponent.EntityComponent;

public class QuestComponent extends EntityComponent
{
	public List<Quest> active, completed;
	
	public QuestComponent()
	{
		active = new ArrayList<Quest>();
		completed = new ArrayList<Quest>();
	}
	
	public void addQuest(Quest quest)
	{
		quest.setQuester(parent);
		
		quest.start();
		
		active.add(quest);
	}
	
	@Override
	public void update(Duration delta) {
		
	}

	@Override
	protected EntityComponent _clone() {
		return null;
	}

}