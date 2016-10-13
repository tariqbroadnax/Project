package GameClient;

import Actions.ActionBuffer;
import GUI.GUI;
import Game.Player;
import Game.Updater;
import Graphic.Camera;

public class ClientUpdater extends Updater
{
	private ClientScene scene;
	
	private ClientNetwork network;
	
	private PlayerRecord record;
	private Camera camera;
	
	public ClientUpdater(
			ClientScene scene,
			ClientNetwork network,
			ActionBuffer buffer,
			PlayerRecord record,
			Camera camera,
			GUI gui)
	{
		super(60);
		
		this.scene = scene;
		this.network = network;
		this.record = record;
		this.camera = camera;
		
		addUpdatable(
				delta -> buffer.invokeAll(),
				delta -> syncScene(),
				delta -> scene.update(delta),
				delta -> focusCamera(),
				delta -> gui.repaintAll());
	}

	private void repaintAll(GUI gui)
	{
		long start = System.currentTimeMillis();
		
		gui.repaintAll();
		
		System.out.println(System.currentTimeMillis() - start);
	}
	
	private void syncScene()
	{		
		network.getServerUpdateBuffer()
			   .invokeAll(scene);
	}
	
	private void focusCamera()
	{
		Player player = record.getPlayer(scene);
		
		if(player == null) return;
		
		camera.setFocus(player.getLoc());
	}
}
