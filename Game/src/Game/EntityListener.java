package Game;

import Entity.Entity;
import EntityComponent.EntityComponent;

public interface EntityListener 
{
	public  void  componentAdded(
			Entity src, EntityComponent comp);
	
	public void componentRemoved(
			Entity src, EntityComponent comp);
	
	public void componentReplaced(
			Entity src, EntityComponent oldComp,
						EntityComponent newComp);
}
