package Game;

public class DialogueTask extends QuestTask
{
	private int dialogueID;

	@Override
	public void start() 
	{
		
	}

	@Override
	public void update() 
	{
		finished = player.getDialogueLog()
				.hasCompletedDialogue(dialogueID);
	}
}
