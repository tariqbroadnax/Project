package Game;

import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import DataStructure.EntityComponentMap;
import Utilities.TileMap;

public class SceneResources
	implements Serializable
{
	public final List<Entity> entities;
	public final Queue<Entity> addQueue, removeQueue;
	
	public final EntityComponentMap componentMap;
	public final TileMap<Entity> tileMap;
	
	private int nextServerID;
	
	public SceneResources()
	{
		entities = new LinkedList<Entity>();
		
		addQueue = new LinkedList<Entity>();
		removeQueue = new LinkedList<Entity>();
	
		tileMap = new TileMap<Entity>(20, 20, 20, 20,
				new Point2D.Double(-200, -200));
		
		componentMap = new EntityComponentMap();
	
		nextServerID = 0;
	}
	
	public int requestServerID()
	{
		return nextServerID++;
	}
}
