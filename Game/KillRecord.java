package Game;

import java.io.Serializable;

public class KillRecord 
	implements Comparable<KillRecord>, Serializable
{
	private int monsterID;
	
	private int killCount;
	
	public KillRecord(int monsterID)
	{
		this.monsterID = monsterID;
		
		killCount = 0;		
	}
	
	public void registerKill()
	{
		killCount++;
	}
	
	public int getMonsterID()
	{
		return monsterID;
	}
	
	public int getKillCount()
	{
		return killCount;
	}

	@Override
	public int compareTo(KillRecord record) 
	{
		return Integer.compare(monsterID, record.monsterID);
	}
}
