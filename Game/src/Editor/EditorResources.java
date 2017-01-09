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

import Editor.actions.Redo;
import Editor.actions.Undo;
import Editor.selection.SelectionHandler;
import Editor.tools.EraseTool;
import Editor.tools.SelectTool;
import Editor.tools.Stamp;
import Editor.tools.Tool;
import EditorGUI.UndoManager;
import Game.Scene;
import Graphic.Camera;
import Tileset.Tileset;
import Utilities.ImagePool;

public class EditorResources 
{
	private static String STATE_FILE_NAME = "editor_state.obj";
	
	public ImagePool pool;
	
	public File file;
	
	private boolean saved;
	
	public Scene scene;
	private Camera camera;	
	
	private EditorState state;
	
	private List<SceneListener> listeners;
	private List<ResourceListener> rlisteners;
	
	// these actions are shared between components
	private Undo undo;
	private Redo redo;
	
	private UndoManager undoManager;
	
	private SelectionHandler selectionHandler;
	
	private Mode mode;
	
	public final Stamp STAMP;
	public final SelectTool SELECT_TOOL;
	public final EraseTool ERASE_TOOL;
	
	private Tool tool;
	
	public EditorResources()
	{
		scene = new Scene();
	
		camera = new Camera();
		
		pool = new ImagePool();
		
		listeners = new ArrayList<SceneListener>();
		rlisteners = new ArrayList<ResourceListener>();
		
		saved = true;
		
		undo = new Undo();
		redo = new Redo();

		undoManager = new UndoManager();
		
		selectionHandler = new SelectionHandler();
	
		STAMP = new Stamp(this);
		SELECT_TOOL = new SelectTool(this);
		ERASE_TOOL = new EraseTool(this);
		
		tool = SELECT_TOOL;
		
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
				list.sceneLoaded();
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
				list.sceneSaved();
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
			
			for(SceneListener list : listeners)
				list.sceneSaved();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void addSceneListener(SceneListener listener)
	{
		listeners.add(listener);
	}
	
	public void addResourceListener(ResourceListener listener)
	{
		rlisteners.add(listener);
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
	
	public UndoManager getUndoManager() {
		return undoManager;
	}
	
	public File getFile() {
		return file;
	}
	
	public Undo getUndoAction() {
		return undo;
	}
	
	public Redo getRedoAction() {
		return redo;
	}	
	
	public SelectionHandler getSelectionHandler() {
		return selectionHandler;
	}
	
	public void setTool(Tool tool) 
	{
		Tool prevTool = this.tool;
		
		this.tool = tool;
	
		for(ResourceListener list : rlisteners)
			list.toolChanged(prevTool, tool);
	}
	
	public Tool getTool() {
		return tool;
	}
	
	public Camera getCamera() {
		return camera;
	}
}
