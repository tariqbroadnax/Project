package Editor;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Entity.Entity;
import Tileset.Tileset;

public class EditorAssets implements Serializable
{
	private List<Entity> entities;
	
	private List<Tileset> tilesets;
	
	private transient List<EditorAssetListener> listeners;
	
	public EditorAssets()
	{
		entities = new ArrayList<Entity>();
	
		tilesets = new ArrayList<Tileset>();
		
		listeners = new ArrayList<EditorAssetListener>();
	}
	
	public void addEntity(Entity ent) 
	{
		entities.add(ent);
		
		notifyListenersEntityAdded(ent);
	}
	
	public void addTileset(Tileset tileset)
	{
		tilesets.add(tileset);
	}
	
	public void removeEntity(Entity ent) 
	{
		if(entities.remove(ent))
			notifyListenersEntityRemoved(ent);
	}
	
	public void removeTileset(Tileset tileset)
	{
		tilesets.remove(tileset);
	}
	
	public List<Tileset> getTilesets() {
		return tilesets;
	}
	
	public void addEditorAssetListener(
			EditorAssetListener listener) {
		listeners.add(listener);
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
	
	private void readObject(java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException
    {
		in.defaultReadObject();
		
		listeners = new ArrayList<EditorAssetListener>();
    }
}
