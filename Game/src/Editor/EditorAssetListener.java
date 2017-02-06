package Editor;

import Entity.Entity;

public interface EditorAssetListener 
{
	public default void entityAdded(Entity ent) {}
	public default void entityRemoved(Entity ent) {}
}
