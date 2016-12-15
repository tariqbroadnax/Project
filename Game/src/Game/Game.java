package Game;

import java.io.File;
import java.io.IOException;

import Actions.ActionBuffer;
import Entity.Fire;
import Entity.Hole;
import Entity.NPC;
import Entity.Player;
import GUI.GUI;
import Graphic.Camera;
import Graphic.Painter;
import Tileset.Tileset;
import Utilities.ImagePool;

public class Game
{	
	private final GUI gui;
	
	private final Updater updater;
	
	private final Painter painter;
		
	private final ActionBuffer actionBuffer;
	
	private final Player player;

	private final Camera camera;
	
	private final ImagePool imgPool;
		
	private final Scene scene;
	
	public Game()
	{
		player = new Player();
		
		camera = new Camera();
	
		imgPool = new ImagePool();
		
		actionBuffer = new ActionBuffer();
		
		scene = new Scene();
		
		updater = new Updater(60);
				
		GameResources resources =
				new GameResources(scene, 
								  null, updater,
								  camera, player,
								  actionBuffer);
		
		gui = new GUI(resources);

		painter = new Painter(gui, camera, imgPool);	
		
		painter.addPaintable(scene);
		
		scene.addEntity(player);
		
		Debug_addTestEntity();
		
		camera.setFocus(player.getLoc());		

		updater.addUpdatable(
				delta -> actionBuffer.invokeAll(),
				delta -> scene.update(delta),
				delta -> painter.paint());
		
		try {
			imgPool.importTileset(new Tileset("fire.png", 4, 8));
			imgPool.importImage(new File("electr_mach.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void Debug_addTestEntity()
	{
		scene.addEntity(new NPC());

		Fire fire = new Fire();
		
		fire.setLoc(-100, 0);
		
		Hole hole = new Hole();
		
		hole.setLoc(-30, -30);
		
		scene.addEntity(fire);
		scene.addEntity(hole);
	}
	
	public void start()
	{
		gui.setVisible(true);
	}
	
	public void stop() 
	{
		gui.setVisible(false);
		
		updater.stop();
	}
}
