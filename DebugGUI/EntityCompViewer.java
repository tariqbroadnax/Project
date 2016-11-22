package DebugGUI;

import EntityComponent.EntityComponent;
import Game.Entity;

public abstract class EntityCompViewer extends Viewer
{
	protected Entity entity;
	
	public EntityCompViewer(Entity entity)
	{
		this.entity = entity;
		
		model.addFieldRecord("Enabled", () -> comp().isEnabled() + "");			
	}
	
	protected abstract EntityComponent comp();
}
