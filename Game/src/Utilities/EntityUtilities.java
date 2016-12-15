package Utilities;

import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.PriorityQueue;

import Entity.Entity;
import EntityComponent.LifetimeComponent;
import EntityComponent.MetaComponent;
import EntityComponent.StatsComponent;

public class EntityUtilities
{	
	public static boolean isDead(Entity e)
	{
		return e.get(LifetimeComponent.class)
				.getLifetime()
				.isLifeOver();
	}
	
	public static Entity nearestPlayer(Point2D.Double start, Collection<Entity> entities)
	{		
		PriorityQueue<Entity> nearbyPlayers = new PriorityQueue<Entity>(
				(e1, e2) -> -Double.compare(start.distance(e1.getLoc()),
										    start.distance(e1.getLoc())));
		
		for(Entity e : entities)
			if(e.get(MetaComponent.class).isPlayer())
				nearbyPlayers.add(e);
		
		return nearbyPlayers.poll();
	}
	
	public static double distanceSq(Entity e1, Entity e2)
	{
		return e1.getLoc().distanceSq(e2.getLoc());
	}
}
