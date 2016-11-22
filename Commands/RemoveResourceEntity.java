package Commands;

import EditorGUI.GUIResources;
import Game.Entity;

public class RemoveResourceEntity extends Command
{
	private Entity entity;
	
	public RemoveResourceEntity(
			GUIResources resources,
			Entity entity)
	{
		super(resources);
		
		this.entity = entity;
	}
		
	@Override
	public void _do() 
	{
		resources.removeEntity(entity);
	}

	@Override
	public void undo() 
	{
		resources.addEntity(entity);
	}	
}
