package GUI;

import javax.swing.JInternalFrame;

import Dialogue.Dialogue;
import Game.GameResources;
import GameClient.GameClientResources;

public class DialogueFrame extends JInternalFrame
{
	private DialoguePanel panel;
	
	public DialogueFrame(GameClientResources resources)
	{
		panel = new DialoguePanel(resources);
		
		add(panel);		
	}
	
	public void setDialogue(Dialogue dialogue)
	{
		panel.setDialogue(dialogue);
	}
}
