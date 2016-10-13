package GameServer;

import Game.Player;

public class ServerPlayerRecord 
{	
	private Player player;
	
	private String userName;
	
	public ServerPlayerRecord(
			Player player,
			String userName)
	{
		this.userName = userName;
		
		this.player = player;
	}
	
	public Player getPlayer()
	{
		return player;
	}
	
	public String getUserName()
	{
		return userName;
	}
	
}
