package Class;

import java.util.ArrayList;
import java.util.List;

import Ability.Ability;

public class Job 
{
	private String name;
	
	private List<Ability> abilities;
	
	public Job()
	{
		name = "JOB";
		
		abilities = new ArrayList<Ability>();
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addAbility(Ability ability) {
		abilities.add(ability);
	}
	
	public void removeAbility(Ability ability) {
		abilities.remove(ability);
	}
}
