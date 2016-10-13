package Game;

import java.awt.geom.Point2D;

import Actions.ActionBuffer;
import Actions.PlayerMovementUpdater;
import GUI.GUI;
import Graphic.Camera;
import Graphic.Painter;
import Utilities.ImagePool;

public class Resources
{
	private final GUI gui;
	
	private final GameUpdater updater;
	
	private final Painter painter;
	
	private final ActionBuffer actionBuffer;
	
	private final Player player;
		
	private final Camera camera;
	
	private final ImagePool imgPool;
		
	private final Scene scene;
	
	private final PlayerMovementUpdater movementUpdater;
	
	public Resources()
	{
		player = new Player();
		
		camera = new Camera();
	
		imgPool = new ImagePool();
		
		actionBuffer = new ActionBuffer();
		
		painter = new Painter(camera, imgPool);	

		gui = new GUI(this);
				
		scene = new Scene();
		
		movementUpdater = new PlayerMovementUpdater(player);
		
		updater = new GameUpdater(
				scene, movementUpdater, 
				actionBuffer, gui);
		
		painter.addPaintable(scene);
		
		scene.addEntity(player);
		
		camera.setFocus(player.getLoc());
		
		imgPool.importFile("pool.txt");			
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
	
	public Player getPlayer()
	{
		return player;
	}
	
	public ImagePool getImagePool()
	{
		return imgPool;
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	
	public Scene getScene()
	{
		return scene;
	}
	
	public ActionBuffer getActionBuffer()
	{
		return actionBuffer;
	}
	
	public PlayerMovementUpdater getPlayerMovementUpdater()
	{
		return movementUpdater;
	}
}
