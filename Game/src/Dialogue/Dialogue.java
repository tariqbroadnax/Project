package Dialogue;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import Event.Event;

public class Dialogue implements Serializable
{	
	private List<String> pages;
	
	private List<Branch> branches;
	
	private List<Event> events;
	
	static int i = 0;
	
	public Dialogue()
	{
		pages = new ArrayList<String>();
		
		branches = new ArrayList<Branch>();
		
		events = new ArrayList<Event>();
		
		// DEBUG ---
		addPage("Twinkle Twinkle Little Star");
		addPage("How I wonder how you are");
		addPage("Up above the sky so high");
		addPage("Like a diamond in the sky");
		addPage("Twinkle Twinkle Little Star");
		addPage("How I wonder how you are");
		
		if(i++ == 0)
		{
			addBranch(new Branch());
			addBranch(new Branch());
		}
		// ---------
	}
	
	public void addPage(String txt) {
		pages.add(txt);
	}
	
	public void removePage(int page) {
		pages.remove(page);
	}
	
	public void setPage(int page, String txt) {
		pages.set(page, txt);
	}
	
	public String getPage(int page) {
		return pages.get(page);
	}
	
	public int pageCount() {
		return pages.size();
	}
	
	public void addBranch(Branch branch) {
		branches.add(branch);
	}
	
	public void removeBranch(Branch branch) {
		branches.remove(branch);
	}
	
	public Branch getBranch(int index) {
		return branches.get(index);
	}
	
	public List<Branch> getBranches() {
		return branches;
	}
	
	public int getBranchCount() {
		return branches.size();
	}
	
	public void addEvent(Event event) {
		events.add(event);
	}
	
	public void removeEvent(Event event) {
		events.remove(event);
	}
	
	public List<Event> getEvents() {
		return events;
	}
}
