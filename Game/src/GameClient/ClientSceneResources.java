package GameClient;

import java.util.LinkedList;
import java.util.List;

import Game.Entity;

public class ClientSceneResources 
{
	public final List<Entity> entities;

	public ClientSceneResources()
	{
		entities = new LinkedList<Entity>();
	}
}
