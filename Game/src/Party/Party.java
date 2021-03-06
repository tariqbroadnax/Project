package Party;

import java.util.ArrayList;
import java.util.List;

import Entity.Entity;
import EntityComponent.StatsComponent;
import Modifiers.MultModifier;
import Stat.Stats;

public class Party 
{
	private List<Entity> members;
	
	private MultModifier expMod;
	
	public Party()
	{
		members = new ArrayList<Entity>();
		
		expMod = new MultModifier(1.2);
	}
	
	public void addMember(Entity member) 
	{
		members.add(member);
	
		Stats stats = member.get(StatsComponent.class)
				.getStats();
				
		stats.addExpModifier(expMod);
		
		updateExpModifier();
	}
	
	public void removeMember(Entity member) 
	{
		members.remove(member);
		
		Stats stats = member.get(StatsComponent.class)
							.getStats();
							
		stats.removeExpModifier(expMod);
		
		updateExpModifier();
	}
	
	private void updateExpModifier()
	{
		double factor = 1.1 + .1 * members.size();
		
		expMod.setFactor(factor);
	}
}
