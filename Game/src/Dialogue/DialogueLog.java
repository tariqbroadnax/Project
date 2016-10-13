package Dialogue;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;

public class DialogueLog
	implements Serializable
{
	private Collection<Integer> completed;
	
	public DialogueLog()
	{
		completed = new HashSet<Integer>();
	}
	
	public void completeDialogue(int dialogueID)
	{
		completed.add(dialogueID);		
	}
	
	public boolean hasCompletedDialogue(int dialogueID)
	{
		return completed.contains(dialogueID);
	}
}
