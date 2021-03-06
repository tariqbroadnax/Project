package Dialogue;

import java.time.Duration;

import EntityComponent.EntityComponent;

public class DialogueComponent extends EntityComponent 
{
	private Dialogue dialogue;
	
	public DialogueComponent()
	{
		dialogue = new Dialogue();
	}
	
	public void setDialogue(Dialogue dialogue) {
		this.dialogue = dialogue;
	}
	
	public Dialogue getDialogue() {
		return dialogue;
	}
	
	@Override
	public void update(Duration delta) {}

	@Override
	protected EntityComponent _clone() 
	{
		return null;
	}
}
