package GUI;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import Actions.HideDialogueFrame;
import Dialogue.Dialogue;
import Dialogue.EndDialogue;
import Dialogue.QuestDialogue;
import Dialogue.ResponseDialogue;
import EntityComponent.QuestComponent;
import Game.Player;
import GameClient.GameClientResources;

public class DialoguePanel extends JPanel
{
	private Dialogue dialogue;
	
	private Player player;
	
	private JPanel buttonPanel;
	
	private JButton endChatButton,
					okButton,
					nextButton,
					acceptButton,
					declineButton;
	
	public DialoguePanel(GameClientResources resources)
	{
		this.player = resources.getPlayer();
		
		endChatButton = new JButton("End Chat");
		okButton = new JButton("OK");
		acceptButton = new JButton("Accept");
		declineButton = new JButton("Decline");
		nextButton = new JButton("Next");
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout());
			
		endChatButton.addActionListener(new HideDialogueFrame(resources));
		okButton.addActionListener(new HideDialogueFrame(resources));
		acceptButton.addActionListener(e -> acceptQuest());
		declineButton.addActionListener(e -> declineQuest());
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		setBackground(Color.white);

		buttonPanel.setAlignmentX(LEFT_ALIGNMENT);
	}
	
	public void setDialogue(Dialogue dialogue)
	{
		if(this.dialogue == dialogue) return;
		
		this.dialogue = dialogue;
		
		removeAll();
		buttonPanel.removeAll();		
		
		JTextArea textArea =
				new DialogueTextArea(
						dialogue.getText(),
						() -> createComp(dialogue));
		
		add(textArea);
	
		revalidate();
	}
	
	private void createComp(Dialogue dialogue)
	{
		if(dialogue instanceof ResponseDialogue) 
		{
			createComp((ResponseDialogue)dialogue);
		}
		else if(dialogue instanceof QuestDialogue) 
		{
			createComp((QuestDialogue)dialogue);
		}
		else if(dialogue instanceof EndDialogue)
		{
			createComp((EndDialogue)dialogue);
		}
	}
	
	private void createComp(ResponseDialogue dialogue)
	{
		for(String response : dialogue.getResponses())
		{
			add(new ResponseLabel(
					response, dialogue.getDialogueOf(response)));
		}	
		
		buttonPanel.add(endChatButton);
		buttonPanel.add(nextButton);
		
		add(buttonPanel);
	}
	
	private void createComp(QuestDialogue dialogue)
	{
		buttonPanel.add(endChatButton);
		buttonPanel.add(acceptButton);
		buttonPanel.add(declineButton);
		
		add(buttonPanel);
	}
	
	private void createComp(EndDialogue dialogue)
	{
		buttonPanel.add(endChatButton);
		buttonPanel.add(okButton);
		
		add(buttonPanel);
	}
	
	private void acceptQuest()
	{
		QuestDialogue dialogue = (QuestDialogue)this.dialogue;
		
		player.get(QuestComponent.class)
			  .getQuestLog()
			  .startQuest(dialogue.getQuest());
		
		setDialogue(dialogue.getAcceptDialogue());
	}
	
	private void declineQuest()
	{
		QuestDialogue dialogue = (QuestDialogue)this.dialogue;

		setDialogue(dialogue.getRejectDialogue());
	}
	
	private class ResponseLabel extends JButton
	{
		private Dialogue dialogue;
		
		private ResponseLabel(String response, Dialogue dialogue)
		{
			this.dialogue = dialogue;
			
			setText(response);
			
			setForeground(Color.blue);
			setBorderPainted(false);
			setBackground(Color.white);
			
			setAlignmentX(LEFT_ALIGNMENT);
			
			addActionListener(e -> setDialogue(dialogue));
		}
	}
}

