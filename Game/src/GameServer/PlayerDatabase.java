package GameServer;

import Game.Player;

public class PlayerDatabase 
{
	public boolean addPlayerRecord(PlayerRecord record,
			String username, 
			String password)
	{
		
	}
	
	public void updatePlayerRecord(PlayerRecord record,
			String username,
			String password)
	{
		
	}
	
	int i = 0;
	
	public PlayerRecord getPlayerRecord(
			String username, 
			String password)
	{
		// FIXME for testing
		Player player = new Player();
		player.setName("player" + i++);
		
		return new PlayerRecord(player);
	}
	
	
}
