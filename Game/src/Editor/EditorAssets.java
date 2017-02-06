package Editor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Entity.Entity;

public class EditorAssets 
{
	private List<Entity> entities;
	
	private List<EditorAssetListener> listeners;
	
	public EditorAssets()
	{
		entities = new ArrayList<Entity>();
	
		listeners = new ArrayList<EditorAssetListener>();
	}
	
	public void addEntity(Entity ent) 
	{
		entities.add(ent);
		
		notifyListenersEntityAdded(ent);
	}
	
	public void addEditorAssetListener(
			EditorAssetListener listener) {
		listeners.add(listener);
	}
	
	public void removeEntity(Entity ent) 
	{
		if(entities.remove(ent))
			notifyListenersEntityRemoved(ent);
	}
	
	public void removeEditorAssetListener(
			EditorAssetListener listener) {
		listeners.remove(listener);
	}

	public List<Entity> getEntities() {
		return Collections.unmodifiableList(entities);
	}
	
	private void notifyListenersEntityAdded(Entity ent)
	{
		for(EditorAssetListener listener : listeners)
			listener.entityAdded(ent);
	}
	
	private void notifyListenersEntityRemoved(Entity ent)
	{
		for(EditorAssetListener listener : listeners)
			listener.entityRemoved(ent);
	}
}
