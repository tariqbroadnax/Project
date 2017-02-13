package Event;

import Dialogue.Dialogue;
import Game.Game;

public class StartDialogue extends Event
{
	private Dialogue dialogue;
	
	public StartDialogue(Game game, Dialogue dialogue) 
	{
		super(game);
	
		this.dialogue = dialogue;
	}

	@Override
	public void run() 
	{
		game.getUI()
			.getHUD()
			.getDialogueArea()
			.setDialogue(dialogue);
	}
}
