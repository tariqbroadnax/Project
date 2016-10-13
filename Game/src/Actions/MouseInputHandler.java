package Actions;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.util.Collection;

import DataStructure.Tile;
import EntityComponent.GraphicsComponent;
import Game.Entity;
import Game.NPC;
import GameClient.GameClientResources;
import GameServer.GameServerResources;
import Graphic.Graphic;
import Utilities.TileMap;

public class MouseInputHandler extends GameAction
{
	public MouseInputHandler(GameClientResources resources) 
	{
		super(resources);
	}

	@Override
	public void executeOnClient() 
	{
		Entity mouseEntity = mouseEntity(mouseLoc);	
		
		if(mouseEntity instanceof NPC)
		{
			new ChatWithNPC(resources)
				.execute((NPC)mouseEntity);
		}
	}

	private Entity mouseEntity(Point mouseLoc)
	{
		Point2D.Double normLoc = 
			resources
				.getCamera()
				.screenLocToNormalLoc(mouseLoc);
	
		TileMap<Entity> tileMap = 
			resources
				.getScene()
				.getResources()
				.tileMap;
		
		Tile tile = tileMap.tileOf(normLoc);
		
		Collection<Entity> entities =
				tileMap.collectionOfTile(tile);
		
		entities.removeIf(e -> !e.contains(GraphicsComponent.class));
		
		for(Entity e : entities)
		{
			Graphic graphic = e.get(GraphicsComponent.class)
							   .getGraphic();
			
			if(graphic.getBound().contains(normLoc))
				return e;
		}
		
		return null;
	}
}
