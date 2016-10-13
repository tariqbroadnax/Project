package EntityManager;

import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

import EntityComponent.SpawnComponent;
import Game.Entity;
import Game.SceneResources;

public class EntitySpawner extends EntityManager
{
	private Collection<Zone> zones;
	
	public EntitySpawner(SceneResources resources)
	{
		super(resources);
		
		zones = new LinkedList<Zone>();
		
		zones.add(new Zone());
	}
	
	public void addZone(Zone zone)
	{
		zones.add(zone);
	}
	
	public void removeZone(Zone zone)
	{
		zones.remove(zone);
	}
	
	public Collection<Zone> getZones()
	{
		return Collections.unmodifiableCollection(zones);
	}

	@Override
	public void update(Duration delta)
	{
		updateZones();
		createEntityChildren();
	}
	
	private void updateZones()
	{
		for(Zone zone : zones)
			zone.update(resources);
	}
	
	private void createEntityChildren()
	{
		Collection<Entity> spawners =			
				resources.componentMap
						 .getEntitiesWithComponent(
								 SpawnComponent.class);
		
		for(Entity spawner : spawners)
			spawner.get(SpawnComponent.class)
				   .createChildren(resources.addQueue);
	}
	
	public String toString()
	{
		String str = "Entity Spawner - ";
		
		for(Zone zone : zones)
			str += "\n" + zone;
		
		return str;
	}
}
