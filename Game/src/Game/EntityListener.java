package Game;

import EntityComponent.EntityComponent;

public interface EntityListener 
{
	public <E extends EntityComponent> void  componentAdded(
			Entity src, Class<E> c);
	
	public <E extends EntityComponent> void componentRemoved(
			Entity src, Class<E> c);
}
