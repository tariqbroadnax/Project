package EditorGUI;

import Game.Entity;

public interface ResourceEntityRemoveListener 
	extends ResourceListener
{
	public default void sceneChanged(GUIResources src){}

	public default void tilesChanged(GUIResources src){}
	
	public default void objectSelected(GUIResources src){};
	
	public default void entityAdded(GUIResources src, Entity entity){};
}
