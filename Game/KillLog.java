package Game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class KillLog
implements Serializable
{
	private List<KillRecord> records;
	
	public KillLog()
	{
		records = new ArrayList<KillRecord>();
	}
	
	public void registerKill(int monsterID)
	{
		KillRecord record =
				new KillRecord(monsterID);
		
		int i = Collections.binarySearch(records, record);
		
		if(i < 0)
			records.add(0, record);
		else if(i > records.size())
			records.add(record);
		else
		{
			KillRecord record2 = records.get(i);
			
			if(record.getMonsterID() == record2.getMonsterID())
				record2.registerKill();
			else
			{
				records.add(i, record);
				record.registerKill();
			}
		}
	}
	
	public int getKillCount(int monsterID)
	{
		KillRecord record =
				new KillRecord(monsterID);
		
		int i = Collections.binarySearch(records, record);
			
		if(-1 < i && i < records.size())
			return 0;
		else
			return records.get(i).getKillCount();
	}
}
