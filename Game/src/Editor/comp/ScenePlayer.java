package Editor.comp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ComponentEvent;

import javax.swing.JPanel;

import Editor.ComponentListener;
import Editor.EditorResources;
import Editor.SceneListener;
import Entity.Entity;
import GUI.UI;
import Game.Game;
import Game.Scene;
import Game.Updater;
import Graphic.Painter;

public class ScenePlayer extends JPanel
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
		
		UI ui = game.getUI();
		
		ui.setViewMode(UI.CANVAS_MODE);
						
		setLayout(new BorderLayout());
		
		JPanel panel = ui.getPanel();
		
		add(panel);
		
		setIgnoreRepaint(true);
		
		addComponentListener(this);
		
		setBackground(Color.white);		
	}
	
	public void start()
	{
		Scene scene = (Scene) resources.scene.clone();
		
		
		Entity player = null;
		for(Entity ent : scene.getEntities())
		{
			if(ent.getCloneParent() == resources.player)
				player = ent;
		}
		
		game.setScene(scene);	
		
		game.setPlayer(player);
		
		game.start();		
		
		System.out.println("Scene Playing");
	}
	
	public void stop()
	{
		game.stop();	
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
//		
//		game.getCamera()
//			.setScreenSize(size);
	}
}
