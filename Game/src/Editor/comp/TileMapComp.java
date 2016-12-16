package Editor.comp;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import Editor.EditorResources;
import Editor.SceneListener;
import Graphic.Camera;
import Tileset.TileMap;

public class TileMapComp extends JComponent
	implements SceneListener
{
	private EditorResources resources;
	private TileMap tm;
	private Camera camera;
	
	public TileMapComp(
			EditorResources resources,
			TileMap tm, Camera camera)
	{
		this.resources = resources;
		this.tm = tm;
		this.camera = camera;
		
		updateLocAndSize();
	}
	
	private void updateLocAndSize()
	{
		Rectangle2D.Double bound = tm.getBound();
		Rectangle scrBound = camera.onScreenBound(bound);
		
		setLocation(scrBound.x, scrBound.y);
		setSize(scrBound.width, scrBound.height);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

	@Override
	public void sceneChanged() 
	{
		updateLocAndSize();
	}
}