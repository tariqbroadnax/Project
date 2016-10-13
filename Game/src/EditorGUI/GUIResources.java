package EditorGUI;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JOptionPane;

import Game.Entity;
import Game.Scene;
import Utilities.ImagePool;

public class GUIResources 
{
	private final ImagePool imgPool;
		
	private Scene scene;
	
	private Object selectedObj;

	private Path sceneFile;
		
	private boolean changesMadeSinceLastSave;
	
	public EditorFrame frame;
	
	private Collection<Entity> entities;
	
	private Collection<ResourceListener> listeners;
	
	private UndoManager undoManager;
	
	public GUIResources()
	{
		imgPool = new ImagePool();
			
		scene = new Scene();
		
		entities = new LinkedList<Entity>();
		
		listeners = new LinkedList<ResourceListener>();
	
		undoManager = new UndoManager();

		frame = new EditorFrame(this);
				
		changesMadeSinceLastSave = false;
	}
	
	public boolean sceneShouldBeChanged()
	{
		return isSceneSaved() || askAndSaveScene();
	}
	
	public void loadScene(Path file)
	{
		try(ObjectInputStream inStream = 
				new ObjectInputStream(
				new FileInputStream(file.toFile())))
		{
			scene = (Scene) inStream.readObject();
			sceneFile = file;
			notifyListeners();
		} catch(IOException | ClassNotFoundException e)
		{
			JOptionPane.showMessageDialog(
					frame, "Invalid File");
		}
	}
	
	public void createNewScene()
	{
		if(sceneShouldBeChanged())
		{
			scene = new Scene();
			sceneFile = null;
			notifyListeners();
		}
	}
		
	public boolean saveSceneToPath(Path path)
	{
		try(ObjectOutputStream outStream =
				new ObjectOutputStream(
				new FileOutputStream(
						path.toString())))
		{
			outStream.writeObject(scene);
			changesMadeSinceLastSave = true;
			return true;
		} catch (IOException e) 
		{
			JOptionPane.showMessageDialog(
					frame, e.getMessage());
			return false;
		} 
	}
	
	public void saveScene()
	{
		if(sceneFile == null)
			saveSceneAs();
		else
			saveSceneToPath(sceneFile);
	}
	
	public void saveSceneAs()
	{
		new SceneSaveDialog(this).setVisible(true);
	}
	
	public boolean askAndSaveScene()
	{
		int reply = JOptionPane.showConfirmDialog(
				null,
				"Do you want to save before exiting this scene?");

		if(reply == JOptionPane.CANCEL_OPTION)
			return false;
		else if(reply == JOptionPane.YES_OPTION)
			saveScene();
		
		return true;
	}
	
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
	
	private void notifyListeners()
	{
		for(ResourceListener listener : listeners)
			listener.sceneChanged(this);
	}
	
	public void setSelectedObj(Object obj)
	{		
		selectedObj = obj;		
		
		for(ResourceListener list : listeners)
			list.objectSelected(this);
	}
	
	public void setSceneFile(Path sceneFile)
	{
		this.sceneFile = sceneFile;
	
		changesSinceLastSave = false;
	}
	
	public void setScene(Scene scene)
	{
		this.scene = scene;
	}
	
	public boolean isSceneSaved()
	{
		return sceneSaved && sceneFile != null;
	}
	
	public ImagePool getImagePool()
	{
		return imgPool;
	}
	
	public Scene getScene()
	{
		return scene;
	}
	
	public Path getSceneFile()
	{
		return sceneFile;
	}
	
	public Object getSelectedObject()
	{
		return selectedObj;
	}
	
	public UndoManager getUndoManager()
	{
		return undoManager;
	}
}
