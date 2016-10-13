package EntityManager;

import java.util.Collection;
import java.util.LinkedList;

import Game.Entity;
import Game.SceneResources;
import Maths.Circle2D;
import TestEntity.TestEntity3;
import Utilities.EntityUtilities;

public class Zone
{
	protected Circle2D.Double area;
	
	protected Entity model;
	
	protected int max;	
	
	
	private Collection<Entity> createdEntities;
	
	public Zone()
	{
		this(new Circle2D.Double(-150, -150, 300),
			 new TestEntity3(), 5);
	}	
	
	public Zone(Circle2D.Double area, Entity model, int max)
	{
		this.area = area;
		this.model = model;
		this.max = max;
	
		createdEntities = new LinkedList<Entity>();
	}
	
	public void update(SceneResources resources)
	{
		removeDeadEntities();
		
		while(moreEntitiesShouldBeCreated())
			spawnNewEntity(resources);
	}
	
	private void spawnNewEntity(SceneResources resources)
	{
		Entity spawned = new Entity(model);
		
		spawned.setLoc(area.randomPoint());
				
		createdEntities.add(spawned);
		resources.addQueue.add(spawned);
	}
	
	private void removeDeadEntities()
	{
		createdEntities.removeIf(e -> EntityUtilities.isDead(e));
	}
	
	private boolean moreEntitiesShouldBeCreated()
	{
		return createdEntities.size() < max;
	}

	public void setArea(Circle2D.Double area)
	{
		this.area = area;
	}
	
	public void setModel(Entity model)
	{
		this.model = model;
	}
	
	public void setMaximumEntities(int max)
	{
		this.max = max;
	}
	
	public Circle2D.Double getArea()
	{
		return area;
	}
	
	public Entity getModel()
	{
		return model;
	}
	
	public int getMaximumEntities()
	{
		return max;
	}
	
	public String toString()
	{
		return "max: " + max + " area: " + area +
			   "\nmodel: \n" + model;
	}
}
