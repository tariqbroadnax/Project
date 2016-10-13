package Actions;

import java.util.Date;

public class GameActionRecord 
{
	private GameAction action;
	
	private Date lastUse;
	
	public GameActionRecord(GameAction action)
	{
		this.action = action;
		
		lastUse = new Date(0);
	}
	
	public void setLastUse(Date lastUse)
	{
		this.lastUse = lastUse;
	}
	
	public GameAction getAction()
	{
		return action;
	}
	
	public Date getLastUse()
	{
		return lastUse;
	}
}
