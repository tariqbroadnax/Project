package Dialogue;

import java.util.Collection;
import java.util.HashMap;

public class ResponseDialogue extends Dialogue
{
	private HashMap<String, Dialogue> responseMap;
	
	public ResponseDialogue()
	{
		super();
		
		responseMap = new HashMap<String, Dialogue>();
	}
	
	public void put(String response, Dialogue dialogue)
	{
		responseMap.put(response, dialogue);
	}
	
	public Collection<String> getResponses()
	{
		return responseMap.keySet();
	}
	
	public Dialogue getDialogueOf(String response)
	{
		return responseMap.get(response);
	}
}
