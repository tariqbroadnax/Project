package Dialogue;

public class Branch 
{	
	private String transition;
	
	private Dialogue dialogue;
	
	public Branch()
	{
		transition = "This is an option";
		
		dialogue = new Dialogue();
	}
	
	public void setTransition(String transition) {
		this.transition = transition;
	}
	
	public void setDialogue(Dialogue dialogue) {
		this.dialogue = dialogue;
	}
	
	public String getTransition() {
		return transition;
	}
	
	public Dialogue getDialogue() {
		return dialogue;
	}
}
