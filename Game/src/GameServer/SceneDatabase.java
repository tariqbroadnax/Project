package GameServer;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.Duration;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.TreeSet;

public class SceneDatabase 
{
	private static final Duration BUFFER_LIFETIME = Duration.ofMinutes(30);
	
	private Map<Integer, SceneNode> sceneBuffer;
	
	private static final String ROOT = "C:\\Users\\Tariq Broadnax\\workspace\\Game\\Scenes\\";
	
	public SceneDatabase()
	{
		sceneBuffer = new HashMap<Integer, SceneNode>();
	}
	
	public void update(Duration delta)
	{
		for(SceneNode node : sceneBuffer.values())
			node.update(delta);
		
		sceneBuffer.values()
				   .removeIf(node -> node.getDurationSinceRequest()
						   				 .compareTo(BUFFER_LIFETIME) > 0);
	}
	
	public boolean addScene(ServerScene scene)
	{
		int sceneID = scene.getSceneID();
		
		Collection<Integer> sceneIDs = sceneIDs();
		
		if(sceneIDs.contains(sceneID))
			return false;
		
		writeSceneToDatabase(scene);
		
		sceneIDs.add(sceneID);
		
		writeSceneIDs(sceneIDs);
		
		return true;
	}
	
	public boolean removeScene(int sceneID)
	{		
		Collection<Integer> sceneIDs = sceneIDs();
		
		if(sceneIDs.contains(sceneID))
			return false;
		
		removeSceneFromDatabase(sceneID);
		
		sceneIDs.remove(sceneID);
		
		writeSceneIDs(sceneIDs);

		return true;
	}
	
	public ServerScene getScene(int sceneID)
	{
		if(!sceneBuffer.containsKey(sceneID))
		{
			Collection<Integer> sceneIDs = sceneIDs();
			
			if(!sceneIDs.contains(sceneID))
				return null;
		
			File file = fileOf(sceneID);
			
			Collection<ServerScene> scenes = readScenesFromDatabase(file);

			addScenesToBuffer(scenes);
		}
		
		SceneNode node = sceneBuffer.get(sceneID);
			
		return node.requestScene();
	}
	
	private void addScenesToBuffer(Collection<ServerScene> scenes)
	{
		for(ServerScene scene : scenes)
		{
			int sceneID = scene.getSceneID();

			if(!sceneBuffer.containsKey(sceneID))
			{
				SceneNode node = new SceneNode(scene);
				
				sceneBuffer.put(sceneID, node);
			}
		}
	}
	
	private File fileOf(int sceneID)
	{
		int left = 10 * sceneID / 10,
			right = left - 1 + 10;
			
		return new File(ROOT + "scene" + left + "_" + right);
	}
	
	private void writeSceneToDatabase(ServerScene scene)
	{
		File file = fileOf(scene.getSceneID());
		
		checkAndCreateFile(file);
		
		Collection<ServerScene> scenes = readScenesFromDatabase(file);
		
		scenes.add(scene);
		
		addScenesToBuffer(scenes);
		writeScenesToDatabase(scenes, file);
	}
	
	private void removeSceneFromDatabase(int sceneID)
	{
		File file = fileOf(sceneID);
				
		Collection<ServerScene> scenes = readScenesFromDatabase(file);

		scenes.removeIf(scene -> scene.getSceneID() == sceneID);
		
		writeScenesToDatabase(scenes, file);
	}
	
	private void writeScenesToDatabase(
			Collection<ServerScene> scenes,
			File file)
	{
		try {
			ObjectOutputStream outStream =
					new ObjectOutputStream(
					new FileOutputStream(file));
			
			for(ServerScene scene : scenes)
				outStream.writeObject(scene);
			
			outStream.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void checkAndCreateFile(File file)
	{
		if(!file.exists())
		{
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private Collection<ServerScene> readScenesFromDatabase(File file)
	{
		Collection<ServerScene> scenes = 
				new LinkedList<ServerScene>();

		try {
			ObjectInputStream inStream = 
					new ObjectInputStream(
					new FileInputStream(file));
			
			scenes.add((ServerScene)inStream.readObject());
			
			inStream.close();
			return scenes;
		} catch(EOFException e) {
			// no scenes in file
			return scenes;
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	private void writeSceneIDs(Collection<Integer> sceneIDs)
	{
		File file = new File(ROOT + "sceneIDs");

		try {
			DataOutputStream outStream = 
					new DataOutputStream(
					new FileOutputStream(file));
			
			for(int i : sceneIDs)
				outStream.writeInt(i);
	
			outStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
	
	public TreeSet<Integer> sceneIDs()
	{
		TreeSet<Integer> sceneIDs =
				new TreeSet<Integer>();
		
		File file = new File(ROOT + "sceneIDs");
		DataInputStream inStream = null;
		try {
			
			inStream = 
					new DataInputStream(
					new FileInputStream(file));
			

			while(inStream.available() > 0)
				sceneIDs.add(inStream.readInt());
						
			inStream.close();
			
			return sceneIDs;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
}

class SceneNode 
{
	private Duration durSinceRequest;
	
	private ServerScene scene;
	
	public SceneNode(ServerScene scene)
	{
		this.scene = scene;
		
		durSinceRequest = Duration.ZERO;
	}
	
	public void update(Duration delta)
	{
		durSinceRequest = durSinceRequest.plus(delta);
	}
	
	public ServerScene requestScene()
	{
		durSinceRequest = Duration.ZERO;

		return scene;
	}
	
	public Duration getDurationSinceRequest()
	{
		return durSinceRequest;
	}
}

