package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import GameClient.ChatCommand;
import GameClient.GameClientResources;
import GameServer.ChatMessage;

public class ChatBox extends JPanel
	implements ActionListener
{
	private JTextArea viewArea;
	
	private JTextField typingField;
	
	private GameClientResources resources;
	
	public ChatBox(GameClientResources resources)
	{
		this.resources = resources;
		
		viewArea = new JTextArea();
		
		
		typingField = new JTextField();
		
		typingField.setFocusable(true);
		typingField.setForeground(Color.black);
				
		viewArea.setEditable(false);
		
		typingField.setEditable(true);
		
		setLayout(new BorderLayout());
		
		setBorder(
				BorderFactory.createLineBorder(Color.lightGray));
		
		add(viewArea, BorderLayout.CENTER);
		add(typingField, BorderLayout.SOUTH);
		
		typingField.addActionListener(this);
		setSize(300, 150);
	}
	
	private String combinedText(Collection<ChatMessage> messages)
	{
		String str = "";
		
		synchronized(messages)
		{
			Iterator<ChatMessage> iter = messages.iterator();
			
			while(iter.hasNext())
			{
				ChatMessage mess = iter.next();
				str += mess.getPlayerName() + ": " +
						   mess.getMessage() + "\n";
			}
		}			
		
		return str;
	}
	
	public void paint(Graphics g)
	{
		String combinedText = combinedText(
				resources.getScene()
						 .getChatLog()
						 .getMessages());
				
		viewArea.setText(combinedText);
		
		super.paint(g);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{		
		String message = typingField.getText();
		
		String playerName = resources.getPlayerRecord()
								 	 .getPlayer(resources.getScene())
									 .getName();
		
		ChatMessage chatMessage =
				new ChatMessage(playerName, message);
		
		resources.getScene()
				 .getChatLog()
				 .add(chatMessage);
		
		ChatCommand command = 
				new ChatCommand(message);
		
		resources.getClientNetwork()
				 .getServerConnector()
				 .getServerConnection()
				 .sendCommand(command);
	
		typingField.setText("");
	}
}
