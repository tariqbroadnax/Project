package EditorGUI;

import Game.Entity;

public interface ResourceEntityAddListener
		extends ResourceListener
{
	public default void sceneChanged(GUIResources src){}

	public default void tilesChanged(GUIResources src){}
	
	public default void objectSelected(GUIResources src){};
	
	public default void entityRemoved(GUIResources src, Entity entity){};
}
