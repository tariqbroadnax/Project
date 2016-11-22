package Game;

public class KillTask extends QuestTask
{
	private int monsterID;
	
	private int start, target;
		
	public KillTask(int monsterID, int target)
	{
		this.monsterID = monsterID;
		
		this.target = target;
	}
	
	@Override
	public void start() 
	{
		KillLog log = player.getKillLog();
		
		start = log.getKillCount(monsterID);
	}

	@Override
	public void update() 
	{
		KillLog log = player.getKillLog();

		int curr = log.getKillCount(monsterID);
		
		if(curr - start >= target)
			finished = true;
	}
}
