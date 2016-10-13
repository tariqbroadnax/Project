package EditorGUI;

import Game.Entity;

public interface ResourceListener 
{
	public void sceneChanged(GUIResources src);

	public void tilesChanged(GUIResources src);
	
	public void objectSelected(GUIResources src);
	
	public void entityAdded(GUIResources src, Entity entity);
	
	public void entityRemoved(GUIResources src, Entity entity);
}


