package Editor;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import Game.Scene;
import Tileset.Tileset;
import Utilities.ImagePool;

public class EditorResources 
{
	private static String STATE_FILE_NAME = "editor_state.obj";
	
	public Object selection;
	
	public ImagePool pool;
	
	public Scene scene;
	
	private EditorState state;
	
	public EditorResources()
	{
		scene = new Scene();
		
		pool = new ImagePool();
		
		try {
			pool.importTileset(new Tileset("gts.png", 1, 2));
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		if(!restoreState())
			state = new EditorState();
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
}
