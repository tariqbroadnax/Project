package EditorGUI;

import Entity.Entity;

public interface SceneChangeListener 
	extends ResourceListener
{
	public default void sceneLoaded(GUIResources src){};
	
	public default void sceneSaved(GUIResources src){};

	public default void objectSelected(GUIResources src){};
	
	public default void entityAdded(
			GUIResources src, Entity entity){};
			
	public default void entityRemoved(
			GUIResources src, Entity entity){};
}
