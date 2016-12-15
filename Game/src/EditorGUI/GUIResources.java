package EditorGUI;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Observer;

import Entity.Entity;
import Game.Scene;
import TileSetPane.TilesetModel;
import Utilities.ImagePool;

public class GUIResources 
{
	private final ImagePool imgPool;
		
	private Scene scene;
	
	private Path sceneFile;
		
	private boolean changesMadeSinceLastSave;
		
	private Collection<Entity> entities;
	
	private Collection<ResourceListener> listeners;
	
	private TilesetModel tileSetModel;
	
	private EntityGroupModel entityGroupModel;
	
	private SelectionManager selectionManager;
	
	public GUIResources()
	{
		imgPool = new ImagePool();
			
		scene = new Scene();
		
		entities = new LinkedList<Entity>();
		
		listeners = new LinkedList<ResourceListener>();
				
		tileSetModel = new TilesetModel(imgPool);
		
		entityGroupModel = new EntityGroupModel();
		
		changesMadeSinceLastSave = false;
		
		selectionManager = new SelectionManager();
		
		addObserverToScene();
	}
	
	private void addObserverToScene()
	{
		Observer o = (obs, src) -> {
			changesMadeSinceLastSave = true;
			notifyListenersOfChangedScene();
		};
		
		scene.addObserver(o);
	}
	
	/* public boolean sceneShouldBeChanged()
	{
		return isSceneSaved() || askAndSaveScene();
	} */
	
	public void loadScene(Path file) 
			throws IOException, ClassNotFoundException
	{
		ObjectInputStream inStream = 
				new ObjectInputStream(
				new FileInputStream(file.toFile()));
		
		scene = (Scene) inStream.readObject();
		sceneFile = file;
		addObserverToScene();
		notifyListenersOfLoadedScene();
		
		inStream.close();
	}
	
	public void createNewScene()
	{
		//if(sceneShouldBeChanged())
		//{
		scene = new Scene();
		sceneFile = null;
		addObserverToScene();
		notifyListenersOfLoadedScene();
		//} 
	}
		
	public void saveSceneToPath(Path path)
		throws IOException
	{
		ObjectOutputStream outStream =
				new ObjectOutputStream(
				new FileOutputStream(
						path.toString()));
	
		outStream.writeObject(scene);
		changesMadeSinceLastSave = false;
		outStream.close();
		
		notifyListenersOfSavedScene();
	}
	
	public boolean canSave()
	{
		return sceneFile != null &&
			   changesMadeSinceLastSave;
	}
	
	public void saveScene() throws IOException
	{
		if(sceneFile == null)
			throw new IllegalStateException(
					"No file to save to");
		/* 
		if(sceneFile == null)
			saveSceneAs();
		else */
		saveSceneToPath(sceneFile);
	}
	
	public void saveSceneAs()
	{
		new SceneSaveDialog(this).setVisible(true);
	}
	
	/*public boolean askAndSaveScene()
	{
		int reply = JOptionPane.showConfirmDialog(
				null,
				"Do you want to save before exiting this scene?");

		if(reply == JOptionPane.CANCEL_OPTION)
			return false;
		else if(reply == JOptionPane.YES_OPTION)
			saveScene();
		
		return true;
	} */
	
	public void notifySceneModified()
	{
		changesMadeSinceLastSave = false;
	}
	
	public void addResourceListener(
			ResourceListener listener)
	{
		listeners.add(listener);
	}
	
	public void addEntity(Entity entity)
	{				
		entities.add(entity);

		LinkedList<ResourceListener> listeners =
				new LinkedList<ResourceListener>(this.listeners);
		
		for(ResourceListener listener : listeners)
			listener.entityAdded(this, entity);		
	}
	
	public void removeEntity(Entity entity)
	{
		entities.remove(entity);
		
		LinkedList<ResourceListener> listeners =
				new LinkedList<ResourceListener>(this.listeners);
		
		for(ResourceListener listener : listeners)
			listener.entityRemoved(this, entity);		
	}
	
	private void notifyListenersOfSavedScene()
	{
		for(ResourceListener listener : listeners)
			listener.sceneSaved(this);
	}
	
	private void notifyListenersOfChangedScene()
	{
		for(ResourceListener listener : listeners)
			listener.sceneChanged(this);
	}
	
	private void notifyListenersOfLoadedScene()
	{
		for(ResourceListener listener : listeners)
			listener.sceneLoaded(this);
	}
	
	public void setSceneFile(Path sceneFile)
	{
		this.sceneFile = sceneFile;
	
		changesMadeSinceLastSave = false;
	}
	
	public void setScene(Scene scene)
	{
		this.scene = scene;
	}
	
	public boolean isSceneSaved() {
		return !changesMadeSinceLastSave;
	}
	
	public ImagePool getImagePool() {
		return imgPool;
	}
	
	public Scene getScene() {
		return scene;
	}
	
	public Path getSceneFile() {
		return sceneFile;
	}
	
	public TilesetModel getTilesetModel() {
		return tileSetModel;
	}
	
	public SelectionManager getSelectionManager() {
		return selectionManager;
	}
}
