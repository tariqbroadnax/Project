package Quest;

import Ability.AbilityListener;
import Entity.Entity;
import EntityComponent.AbilityComponent;

public class KillTask extends Task
	implements AbilityListener
{
	private int id, curr, target;
	
	private Entity ent;

	public KillTask()
	{
		id = -1;
		curr = 0;
		target = 5;
	}
	
	@Override
	public void start(Entity ent) 
	{
		this.ent = ent;
		
		ent.get(AbilityComponent.class)
		   .addAbilityListener(this);
	}
	
	public void setTargetID(int id) {
		this.id = id;
	}
	
	public void setTargetKillCount(int target) {
		this.target = target;
	}
	
	public int getTargetID() {
		return id;
	}
	
	public int getTargetKillCount() {
		return target;
	}
	
	public int getCurrentKillCount() {
		return curr;
	}
	
	@Override
	public void entityKilled(Entity ent) 
	{
		if(ent.getID() == id)
		{
			curr++;
			
			// DEBUG ---
			System.out.println("Progress: " + curr + "/" + target);
			// ---------
			
			if(curr == target)
			{
				taskCompleted();
				
				this.ent.get(AbilityComponent.class)
				   		.removeAbilityListener(this);
			}
		}
	}
}