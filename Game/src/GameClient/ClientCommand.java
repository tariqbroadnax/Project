package GameClient;

import java.io.Serializable;

import Game.Player;
import GameServer.ServerSceneNetwork;

public interface ClientCommand extends Serializable
{
	public void execute(Player player,
			ServerSceneNetwork sceneNetwork);
}
