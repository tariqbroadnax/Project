package GameServer;

import Game.Player;

public class PlayerRecord 
{
	private Player player;
	
	public PlayerRecord(Player player)
	{
		this.player = player;
	}
	
	public Player getPlayer()
	{
		return player;
	}
}
