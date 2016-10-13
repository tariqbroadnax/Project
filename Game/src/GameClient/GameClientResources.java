package GameClient;

import Actions.ActionBuffer;
import GUI.GUI;
import Game.Updater;
import Graphic.Camera;
import Graphic.Painter;
import Utilities.ImagePool;

public class GameClientResources 
{
	private final GUI gui;

	private final ClientUpdater updater;
	
	private final Painter painter;
	
	private final ActionBuffer actionBuffer;
		
	private final PlayerRecord playerRecord;
		
	private final Camera camera;
	
	private final ImagePool imgPool;
	
	private final ClientScene scene;
	
	private final ClientNetwork network;
					
	public GameClientResources()
	{		
		playerRecord = new PlayerRecord();
		
		network = new ClientNetwork(playerRecord);

		network.start();
		
		imgPool = new ImagePool();
		
		camera = new Camera();
	
		painter = new Painter(camera);	

		gui = new GUI(this);

		actionBuffer = new ActionBuffer();
				
		scene = new ClientScene();
	
		updater = new ClientUpdater(
				scene, network,
				actionBuffer, playerRecord,
				camera, gui);
		
		painter.addPaintable(scene);
				
		imgPool.importFile("pool.txt");
	}

	public ActionBuffer getActionBuffer()
	{
		return actionBuffer;
	}
	
	public ClientNetwork getClientNetwork()
	{
		return network;
	}
	
	public GUI getGUI()
	{
		return gui;
	}
	
	public Updater getUpdater()
	{
		return updater;
	}
	
	public Painter getPainter()
	{
		return painter;
	}
	
	public ImagePool getImagePool()
	{
		return imgPool;
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	
	public PlayerRecord getPlayerRecord()
	{
		return playerRecord;
	}
	
	public ClientScene getScene()
	{
		return scene;
	}
}
