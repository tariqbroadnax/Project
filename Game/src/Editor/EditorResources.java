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
import Editor.tools.MoveTool;
import Editor.tools.SelectTool;
import Editor.tools.Stamp;
import Editor.tools.Tool;
import EditorGUI.UndoManager;
import Entity.Entity;
import Game.GameSave;
import Game.Scene;
import Graphic.Camera;
import Tileset.Tileset;
import Utilities.ImagePool;

public class EditorResources 
{
	private static String STATE_FILE_NAME = "editor_state.obj";
		
	public ImagePool pool;
	
	public File file;
	
	private GameSave save;
	
	private boolean saved;
	
	public Scene scene;
	public Entity player;
	private Camera camera;	
	
	private EditorState state;
	
	private List<SceneListener> listeners;
	private List<ResourceListener> rlisteners;
	
	public static Undo undo;
	public static Redo redo;
	
	private UndoManager undoManager;
	
	private SelectionHandler selectionHandler;
	
	private boolean tiledMode;
	
	public final Stamp STAMP;
	public final SelectTool SELECT_TOOL;
	public final EraseTool ERASE_TOOL;
	public final MoveTool MOVE_TOOL;
	
	private Tool tool;
	
	private EditorAssets editorAssets;
	
	private SnapSettings snapSettings;

	private JFileChooser fc;

	public EditorResources()
	{
		scene = new Scene();
	
		camera = new Camera();
		
		pool = new ImagePool();
		
		listeners = new ArrayList<SceneListener>();
		rlisteners = new ArrayList<ResourceListener>();
		
		save = new GameSave();
		
		saved = true;
		
		fc = new JFileChooser();
		
		undo = new Undo();
		redo = new Redo();

		undoManager = new UndoManager();
		
		selectionHandler = new SelectionHandler();
	
		STAMP = new Stamp(this);
		SELECT_TOOL = new SelectTool(this);
		ERASE_TOOL = new EraseTool(this);
		MOVE_TOOL = new MoveTool(this);
		
		editorAssets = new EditorAssets();
		
		snapSettings = new SnapSettings();
		
		tool = SELECT_TOOL;
		
		tiledMode = true;
		
		try {
			pool.importTileset(new Tileset("GrassTileSet.png", 1, 2), 320, 160);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!restoreState())
			state = new EditorState();	
		
		camera.setZoom(0.8);
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
			out.writeObject(editorAssets);
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
	
	public void startPlayingScene()
	{
		for(SceneListener list : listeners)
			list.scenePlayerStarted();
	}
	
	public void stopPlayingScene()
	{
		for(SceneListener list : listeners)
			list.scenePlayerStopped();
	}
	
	private boolean restoreState()
	{
		try (ObjectInputStream in = 
				new ObjectInputStream(
				new FileInputStream(STATE_FILE_NAME)))
		{	
			state = (EditorState) in.readObject();
			editorAssets = (EditorAssets) in.readObject();
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
	
	public EditorAssets getEditorAssets() {
		return editorAssets;
	}
	
	public void setTiledMode(boolean tiledMode) 
	{
		this.tiledMode = tiledMode;
		
		for(ResourceListener list : rlisteners)
			list.tiledModeChanged(tiledMode);
	}
	
	public boolean tiledMode() {
		return tiledMode;
	}
	
	public Camera getCamera() {
		return camera;
	}
	
	public SnapSettings getSnapSettings() {
		return snapSettings;
	}
	
	public GameSave getGameSave() {
		return save;
	}
}
