package EditorGUI;

import Entity.Entity;

public interface ResourceListener 
{
	public void sceneLoaded(GUIResources src);
	
	public void sceneChanged(GUIResources src);

	public void sceneSaved(GUIResources src);
		
	public void objectSelected(GUIResources src);
	
	public void entityAdded(GUIResources src, Entity entity);
	
	public void entityRemoved(GUIResources src, Entity entity);
}


