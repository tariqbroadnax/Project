package EditorGUI;

import Game.Entity;

public class EntityWrapper 
{
	public final Entity entity;
	public final EntityResourceType type;
	
	public EntityWrapper(
			Entity entity, EntityResourceType type)
	{
		this.entity = entity;
		this.type = type;
	}
}
