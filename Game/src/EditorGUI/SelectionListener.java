package EditorGUI;

import Game.Entity;

public interface SelectionListener 
	extends ResourceListener
{
	public default void sceneChanged(GUIResources src){};

	public default void tilesChanged(GUIResources src){};
	
	public default void entityAdded(
			GUIResources src, Entity entity){};
			
	public default void entityRemoved(
			GUIResources src, Entity entity){};		
}
