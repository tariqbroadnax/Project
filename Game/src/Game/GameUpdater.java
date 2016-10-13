package Game;

import Actions.ActionBuffer;
import Actions.PlayerMovementUpdater;
import GUI.GUI;

public class GameUpdater extends Updater
{
	public GameUpdater(
			Scene scene,
			PlayerMovementUpdater moveUpdater,
			ActionBuffer buffer,
			GUI gui)
	{
		super(60);
		
		addUpdatable(
				delta -> buffer.invokeAll(),
				delta -> moveUpdater.update(delta),
				delta -> scene.update(delta),
				delta -> gui.repaintAll());
	}
}
