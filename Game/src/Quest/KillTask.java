package Quest;

import Entity.Entity;
import EntityComponent.CombatComponent;
import EntityComponent.CombatListener;

public class KillTask extends Task
	implements CombatListener
{
	private Entity targetEnt;

	private int curr, target;
	
	public KillTask()
	{
		targetEnt = null;
		curr = 0;
		target = 5;
	}
	
	public KillTask(KillTask task)
	{
		targetEnt = task.targetEnt;
		curr = 0;
		target = task.target;
	}

	@Override
	public void start() 
	{
		tasker.get(CombatComponent.class)
			  .addCombatListener(this);
	}
	
	@Override
	public void stop()
	{
		tasker.get(CombatComponent.class)
		  	  .removeCombatListener(this);
	}

	@Override
	public boolean isFinished() {
		return curr >= target;
	}

	@Override
	public Object clone() {
		return new KillTask(this);
	}

	@Override
	public void entityKilled(Entity ent) 
	{
		if(ent == targetEnt)
			target++;
	}
}
