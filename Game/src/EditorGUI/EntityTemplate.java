package EditorGUI;

import Entity.Entity;

public class EntityTemplate 
{
	private String name;
	
	private Entity model;
	
	public EntityTemplate(String name, Entity model)
	{
		this.name = name;
		this.model = model;
	}
	
	public String getName()
	{
		return name;
	}
	
	public Entity getModel()
	{
		return model;
	}
}
