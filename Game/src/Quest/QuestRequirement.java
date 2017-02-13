package Quest;

import Entity.Entity;

public class QuestRequirement implements Requirement
{
	private Quest quest;
	
	public QuestRequirement(){}

	public QuestRequirement(Quest quest)
	{
		this.quest = quest;
	}
	
	@Override
	public boolean isSatisfied(Entity ent) 
	{
		return ent.get(QuestComponent.class)
				  .getCompletedQuests()
				  .contains(quest);
	}
	
	public void setQuest(Quest quest) {
		this.quest = quest;
	}
}
