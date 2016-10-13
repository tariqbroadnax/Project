package GameClient;

import Game.Player;

public class PlayerRecord 
{
	private int entityID;
	
	public PlayerRecord()
	{
		entityID = -1;
	}
	
	public Player getPlayer(ClientScene scene)
	{
		return (Player) scene.getEntity(entityID);
	}
	
	public void setEntityID(int entityID)
	{
		this.entityID = entityID;
	}
	
	public int getEntityID()
	{
		return entityID;
	}
}
