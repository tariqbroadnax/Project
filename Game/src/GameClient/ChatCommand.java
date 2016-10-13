package GameClient;

import Game.Player;
import GameServer.ChatMessage;
import GameServer.ServerScene;
import GameServer.ServerSceneNetwork;

public class ChatCommand implements ClientCommand
{
	private String message;
	
	public ChatCommand(String message)
	{
		this.message = message;
	}
	
	@Override
	public void execute(Player player, ServerSceneNetwork sceneNetwork) 
	{
		ServerScene scene = sceneNetwork.getSceneOf(player);
		
		ChatMessage chatMessage = 
				new ChatMessage(player.getName(), message);
		
		scene.getChatLog()
			 .add(chatMessage);		
	}
}
