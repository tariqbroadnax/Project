package GameClient;

import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;

import DebugGUI.EntityViewerFrame;
import EntityComponent.GraphicsComponent;
import Game.Entity;
import Game.NPC;
import Game.Updatable;
import GameServer.ChatLog;
import Graphic.GraphicsContext;
import Graphic.Paintable;
import Graphic.TileMap2;
import Movement.MovementComponent;
import Movement.TargetedMovement;
import Utilities.ImagePool;

public class ClientScene implements Updatable, Paintable
{	
	private HashMap<Integer, Entity> entities;
	
	private ChatLog chatLog;
	
	private ImagePool imgPool;
	
	private TileMap2 tileMap;
	
	public ClientScene()
	{
		entities = new HashMap<Integer, Entity>();

		chatLog = new ChatLog();
		
		imgPool = new ImagePool();
		
		tileMap = new TileMap2();
		
		imgPool.importFile("pool.txt");
		
		addEntity(new NPC());
	}

	public void addEntity(Entity e)
	{
		formatEntity(e);
		entities.put(e.getEntityID(), e);		
	}
	
	private void formatEntity(Entity e)
	{
		/*
		 * each scene entity has a 
		 * target movement for interpolation
		 */
		
		MovementComponent comp = 
				new MovementComponent();
		
		TargetedMovement movement =
				new TargetedMovement();
		
		movement.setEnabled(true);
		
		comp.setNormalMovement(movement);
		
		e.add(comp);
	}
	
	public void removeEntity(int entityID)
	{
		entities.remove(entityID);
	}
	
	public Entity getEntity(int entityID)
	{
		return entities.get(entityID);
	}
	
	@Override
	public void paint(GraphicsContext gc) 
	{
		gc.setImagePool(imgPool);
		
		tileMap.paint(gc);
		paintEntities(gc);
	}
	
	public void synchronizeEntities(Collection<Entity> updatedEntities) 
	{
		//System.out.println(entities.keySet());
		for(Entity e : updatedEntities)
		{
			int entityID = e.getEntityID();
			
			if(entities.containsKey(entityID))
				entities.get(entityID)
						.interpolate(e);
			else
			{
				//Player player = (Player)e;
				//System.out.println(player.getName());
				addEntity(e);
				new EntityViewerFrame(e);
			}
		}
	}

	@Override
	public void update(Duration delta) 
	{
		updateEntities(delta);
	}
	
	protected void updateEntities(Duration delta)
	{
		for(Entity e : entities.values())
			e.update(delta);
	}
	
	public void paintEntities(GraphicsContext gc)
	{		
		for(Entity e : entities.values())
		{
			e.get(GraphicsComponent.class)
			 .paint(gc);			
		}
	}

	public ChatLog getChatLog() 
	{
		return chatLog;
	}
}
