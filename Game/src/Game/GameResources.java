package Game;

import Actions.ActionBuffer;
import Actions.MouseInputHandler;
import Entity.Player;
import Graphic.Camera;
import Graphic.Painter;

public class GameResources
{
	public final Painter painter;
	public final Updater updater;
	public final Camera camera;
	public final Player player;
	public final ActionBuffer buffer;
	public final Scene scene;
	
	public MouseInputHandler miHandler;
	
	public GameResources(
			Scene scene,
			Painter painter, Updater updater,
			Camera camera, Player player,
			ActionBuffer buffer)
	{
		this.scene = scene;
		this.painter = painter;
		this.updater = updater;
		this.camera = camera;
		this.player = player;
		this.buffer = buffer;
		
		miHandler = new MouseInputHandler(this);
	}
}
