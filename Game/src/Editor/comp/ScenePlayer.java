package Editor.comp;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;

import Editor.ComponentListener;
import Editor.EditorResources;
import Editor.SceneListener;
import Game.Game;
import Game.Scene;
import Game.Updater;
import Graphic.Camera;
import Graphic.Painter;

public class ScenePlayer extends Canvas
	implements SceneListener,
			   ComponentListener
{
	private EditorResources resources;
	
	private Updater updater;
	
	private Painter painter;
	
	private Game game;
		
	public ScenePlayer(EditorResources resources)
	{
		this.resources = resources;
		
		game = new Game();
				
		Camera camera = game.getCamera();
		
		painter = new Painter(this, camera);
	
		updater = new Updater(250);
				
		setIgnoreRepaint(true);
		
		addComponentListener(this);
		
		//resources.addSceneListener(this);
		
		setBackground(Color.white);
		
		//camera.setFocus(-100, -100);
	}
	
	private void tryAndPaint()
	{
		try {
			painter.paint();
		} catch(IllegalStateException e) {}
		
		/* manages case where painter is still painting
		 * when this component is not visible / buffers gone
		 */
	}
	
	public void addNotify()
	{
		super.addNotify();
	
		createBufferStrategy(2);
	}

	public void start()
	{
		updater.clear();
		painter.clear();
		
		Scene scene = (Scene) resources.scene.clone();

		updater.addUpdatable(
					d -> scene.update(d),
					d -> tryAndPaint());

		painter.addPaintable(scene);
		
		game.setScene(scene);
		
		updater.start();
	}
	
	public void stop()
	{
		updater.stop();		
	}
	
	@Override
	public void scenePlayerStarted() {
		start();
	}
	
	@Override
	public void scenePlayerStopped() {
		stop();
	}
	
	public void componentResized(ComponentEvent e) 
	{
		Dimension size = getSize();
		
		game.getCamera()
			.setScreenSize(size);
	}
}