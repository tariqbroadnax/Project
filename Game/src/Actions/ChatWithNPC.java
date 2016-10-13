package Actions;

import GUI.DialogueFrame;
import Game.NPC;
import GameClient.GameClientResources;

public class ChatWithNPC extends GameAction
{
	public ChatWithNPC(GameClientResources resources) 
	{
		super(resources);
	}

	@Override
	public void executeOnClient() {}
	
	public void execute(NPC npc) 
	{
		DialogueFrame frame = 
			resources
				.getGUI()
				.getHUD()
				.getDialogueFrame();
		
		frame.setVisible(true);
		frame.setDialogue(npc.getDialogue(resources.getPlayer()));
	}
}
