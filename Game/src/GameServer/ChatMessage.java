package GameServer;

import java.io.Serializable;

public class ChatMessage 
	implements Serializable
{
	private String playerName,
				   message;
	
	public ChatMessage(
			String playerName, 
			String message)
	{
		this.playerName = playerName;
		this.message = message;
	}
	
	public String getPlayerName()
	{
		return playerName;
	}
	
	public String getMessage()
	{
		return message;
	}
}
