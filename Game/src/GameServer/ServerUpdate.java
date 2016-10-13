package GameServer;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

import Game.Entity;
import GameClient.ClientScene;

public class ServerUpdate 
	implements Serializable
{
	public Date creation;
	
	private Collection<Entity> updatedEntities,
							   removedEntities;
	
	private Collection<ChatMessage> messages;
	
	public ServerUpdate(
			Collection<Entity> updatedEntities,
			Collection<Entity> removedEntities,
			Collection<ChatMessage> messages)
	{
		// null is allowed because it reduces bandwidth
		
		this.updatedEntities = updatedEntities;
	
		this.removedEntities = removedEntities;
		
		this.messages = messages;
		
		creation = new Date();	
	}
	
	public void updateScene(ClientScene scene)
	{
		synchronizeEntities(scene);
		removeEntities(scene);
		addMessages(scene);
	}
	
	private void synchronizeEntities(ClientScene scene)
	{
		if(updatedEntities != null)
			scene.synchronizeEntities(updatedEntities);
	}
	
	private void removeEntities(ClientScene scene)
	{
		if(removedEntities == null) return;

		for(Entity e : removedEntities)
		{
			int entityID = e.getEntityID();
			
			scene.removeEntity(entityID);
		}
	}
	
	private void addMessages(ClientScene scene)
	{		
		for(ChatMessage message : messages)
			scene.getChatLog()
				 .add(message);
	}
	
	public Date getCreationDate()
	{
		return creation;
	}
}
