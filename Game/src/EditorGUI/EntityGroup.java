package EditorGUI;

import java.util.ArrayList;
import java.util.List;

import Entity.Entity;

public class EntityGroup
{
	private List<Entity> entities;
	
	public EntityGroup()
	{
		entities = new ArrayList<Entity>();
	}
	
	public void add(Entity e)
	{
		entities.add(e);
	}
	
	public void remove(Entity e)
	{
		entities.remove(e);
	}
}
