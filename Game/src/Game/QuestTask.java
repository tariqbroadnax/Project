package Game;

public abstract class QuestTask 
{
	protected boolean finished;
	
	protected Player player;
	
	public QuestTask()
	{
		finished = false;
	}
	
	public abstract void start();

	public abstract void update();
	
	public boolean isFinished()
	{
		return finished;
	}
	
	public void setPlayer(Player player)
	{
		this.player = player;
	}
}
