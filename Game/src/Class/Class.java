package Class;

import java.util.ArrayList;
import java.util.List;

public class Class 
{
	private String name;
	
	private List<Job> jobs;
	
	public Class()
	{
		name = "Class";
		
		jobs = new ArrayList<Job>();
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void addJob(Job job) {
		jobs.add(job);
	}
	
	public void removeJob(Job job) {
		jobs.remove(job);
	}
}
