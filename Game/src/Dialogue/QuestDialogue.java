package Dialogue;

import Game.Quest;

public class QuestDialogue extends Dialogue
{
	private Quest quest;
	
	private Dialogue acceptDialogue,
					 rejectDialogue;
	
	public QuestDialogue()
	{
		super();
		
		quest = null;
		
		acceptDialogue = 
		rejectDialogue = null;
	}
	
	public void setQuest(Quest quest)
	{
		this.quest = quest;
	}
	
	public void setAcceptDialogue(Dialogue acceptDialogue)
	{
		this.acceptDialogue = acceptDialogue;
	}
	
	public void setRejectDialogue(Dialogue rejectDialogue)
	{
		this.rejectDialogue = rejectDialogue;
	}
	
	public Quest getQuest()
	{
		return quest;
	}
	
	public Dialogue getAcceptDialogue()
	{
		return acceptDialogue;
	}
	
	public Dialogue getRejectDialogue()
	{
		return rejectDialogue;
	}
}
