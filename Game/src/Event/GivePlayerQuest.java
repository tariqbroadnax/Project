package Event;

import Game.Game;
import Quest.Quest;
import Quest.QuestComponent;

public class GivePlayerQuest extends Event
{
	private Quest quest;
	
	public GivePlayerQuest(Game game, Quest quest)
	{
		super(game);
		
		this.quest = quest;
	}
	
	@Override
	public void run() 
	{
		QuestComponent comp = game.getPlayer()
								  .get(QuestComponent.class);
		
		comp.addQuest(quest);
	}

}
