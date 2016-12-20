package Editor;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import EditorGUI.MouseListener;
import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Graphic.Camera;

public class SceneEntityComponent extends JComponent
	implements SceneListener,
				MouseListener
{
	private EditorResources resources;
	
	private Entity ent;
	private Camera camera;
	
	public SceneEntityComponent(
			EditorResources resources, Entity ent,
			Camera camera)
	{
		this.resources = resources;
		this.ent = ent;
		this.camera = camera;
		
		addMouseListener(this);
		resources.addSceneListener(this);

		updateLocAndSize();
	}
	
	private void updateLocAndSize()
	{
		GraphicsComponent comp = ent.get(GraphicsComponent.class);
		
		comp.updateGraphicLocation();
		
		Rectangle2D.Double bound = 
			comp.getGraphic()
			    .getBound();
		
		Rectangle scrBound = camera.boundOnScreen(bound);

		setBounds(scrBound);
	}
	
	public void sceneChanged()
	{
		updateLocAndSize();
	}

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(resources.selection == ent)
		{
			Color selectionColor = new Color(0,0,255,120);
			Dimension size = getSize();
			
			g.setColor(selectionColor);
			g.fillRect(0, 0, size.width, size.height);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{		
		if(resources.selection == null ||
		   resources.sceneSelection)
		{
			resources.selection = ent;
			resources.sceneSelection = true;
			repaint();
		}
	}
}