package GUI;

import Actions.ActionBuffer;
import Entity.Entity;
import Game.Scene;
import Game.Updater;
import Graphic.Camera;
import Graphic.Painter;

public class UI
{
	private UIController controller;
	
	private GFrame frame;
	
	private Painter painter;
	
	private Camera camera;
	
	private ActionBuffer buffer;
	
	public UI(Scene scene, Updater updater, Entity player)
	{		
		controller = new UIController(this);
		
		frame = new GFrame(updater);
	
		GLayeredPane layers = new GLayeredPane();
		
		HUD hud = new HUD();
		
		camera = new Camera();
		
		painter = new Painter(frame, camera);
		
		buffer = new ActionBuffer();
		
		painter.addPaintable(scene);
		
		updater.addUpdatable(d -> painter.paint(),
							 d -> buffer.invokeAll());
		
		camera.setFocus(player.getLoc());
		
		MouseController mouseControl = new MouseController(
				player, camera, buffer);
		
		frame.addMouseListener(mouseControl);
		
		frame.getRootPane()
			 .setOpaque(false);
	
		frame.setContentPane(layers);
		
		layers.addAndSetLayer(hud, 1);
	}
	
	public void setGUIVisible(boolean visible) {
		frame.setVisible(visible);
	}
}