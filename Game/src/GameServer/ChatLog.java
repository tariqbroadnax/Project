package GameServer;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

public class ChatLog 
{
	private LinkedList<ChatMessage> messages,
									newMessages;
	
	public ChatLog()
	{
		messages = new LinkedList<ChatMessage>();
		newMessages = new LinkedList<ChatMessage>();
	}

	public void add(ChatMessage message)
	{
		messages.add(message);
		newMessages.add(message);
	}
	
	public Collection<ChatMessage> getMessages()
	{
		return Collections.unmodifiableCollection(messages);
	}
	
	public Collection<ChatMessage> getNewMessages()
	{
		return Collections.unmodifiableCollection(newMessages);
	}
	
	public void flushNewMessages()
	{
		newMessages.clear();
	}
}
