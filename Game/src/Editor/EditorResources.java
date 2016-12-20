package Editor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import Game.Scene;
import Tileset.Tileset;
import Utilities.ImagePool;

public class EditorResources 
{
	private static String STATE_FILE_NAME = "editor_state.obj";
	
	public Object selection;
	public boolean sceneSelection;
	
	public ImagePool pool;
	
	public File file;
	
	private boolean saved;
	
	public Scene scene;
	
	private EditorState state;
	
	private List<SceneListener> listeners;
	
	public EditorResources()
	{
		scene = new Scene();
	
		pool = new ImagePool();

		listeners = new ArrayList<SceneListener>();

		saved = true;
		
		try {
			pool.importTileset(new Tileset("GrassTileSet.png", 1, 2), 320, 160);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!restoreState())
			state = new EditorState();
	}
	
	public void _new()
	{
		if(!saved)
		{
			int returnVal = JOptionPane.showConfirmDialog(
					null, "Do you want save changes?");
			
			if(returnVal == JOptionPane.YES_OPTION)
			{
				save();
				
				file = null;
				scene = new Scene();
				
				for(SceneListener list : listeners)
				{
					list.sceneLoaded();
					list.sceneChanged();
				}
			}
			
		}
		else
		{
			file = null;
			scene = new Scene();
		
			for(SceneListener list : listeners)
			{
				list.sceneLoaded();
				list.sceneChanged();
			}
		}
	}
	
	public void save()
	{
		if(file == null)
			saveAs();
		else
			writeScene();
	}
	
	public void saveAs()
	{
		JFileChooser fc = new JFileChooser();
		
		int returnVal = fc.showSaveDialog(null);
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			file = fc.getSelectedFile();
			writeScene();
			
			for(SceneListener list : listeners)
				list.sceneLoaded();
		}
	}
	
	private void writeScene()
	{
		try(ObjectOutputStream out = 
				new ObjectOutputStream(
				new FileOutputStream(file)))
		{
			out.writeObject(scene);
			out.close();
			saved = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addSceneListener(SceneListener listener)
	{
		listeners.add(listener);
	}
	
	public void notifyOfSceneChange()
	{	
		saved = false;

		// avoid concurrent modification
		ArrayList<SceneListener> listeners =
				new ArrayList<SceneListener>(this.listeners);
				
		for(SceneListener listener : listeners)
			listener.sceneChanged();
	}
	
	public void saveState()
	{
		try (ObjectOutputStream out = 
				new ObjectOutputStream(
				new FileOutputStream(STATE_FILE_NAME)))
		{
			out.writeObject(state);
			
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public void open()
	{
		JFileChooser fc = new JFileChooser();
		
		int returnVal = fc.showOpenDialog(null);
		
		if(returnVal == JFileChooser.APPROVE_OPTION)
		{
			file = fc.getSelectedFile();
			
			for(SceneListener list : listeners)
				list.sceneLoaded();
		}
	}
	
	private boolean restoreState()
	{
		try (ObjectInputStream in = 
				new ObjectInputStream(
				new FileInputStream(STATE_FILE_NAME)))
		{	
			state = (EditorState) in.readObject();
			return true;
			
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		}
	}
	
	public EditorState getState() {
		return state;
	}
	
	public File getFile() {
		return file;
	}
}
