package Editor.entity_selector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;
import javax.swing.SwingUtilities;

import Editor.EditorResources;
import Editor.selection.SelectionHandler;
import EditorGUI.MouseListener;
import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Graphic.Camera;
import Graphic.Graphic;
import Graphic.GraphicsContext;
import Maths.Dimension2D;

public class EntityComponent extends JComponent 
	implements MouseListener
{
	private EditorResources resources;
	
	private Entity ent;
	
	private EntitySelector parent;
	
	public EntityComponent(
			EditorResources resources,
			Entity ent, EntitySelector parent)
	{
		this.resources = resources;
		this.ent = ent;
		this.parent = parent;
		
		addMouseListener(this);
		
		updateSize();
	}
	
	private void updateSize()
	{
		Dimension scrSize;
		
		if(ent.contains(GraphicsComponent.class))
		{
			Camera camera = new Camera();
	
			Dimension2D.Double graphSize = 
				ent.get(GraphicsComponent.class)
				   .getGraphic()
				   .getSize();
			
			scrSize = camera.sizeOnScreen(graphSize);
		}
		else
			scrSize = new Dimension(40, 40);
		
		setPreferredSize(scrSize);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		paintEntity(g);
		checkAndPaintSelectionIndicator(g);
	}
	
	private void paintEntity(Graphics g)
	{
		if(ent.contains(GraphicsComponent.class))
		{			
			Graphic graph = ent.get(GraphicsComponent.class)
					   		   .getGraphic();
			
			Camera camera = new Camera();

			Rectangle2D.Double graphBound = graph.getBound(),
							   viewBound = camera.normalViewBound();
				
			camera.setFocus(graphBound.x + viewBound.width/2,
							graphBound.y + viewBound.height/2);
			

			GraphicsContext gc = new GraphicsContext(
					g, camera);
		
			graph.paint(gc);
					
			gc.g2d.dispose();
		}
		else
		{
			Dimension size = getSize();
		
			g.setColor(Color.black);
			g.fillRect(0, 0, size.width, size.height);
		}
	}
	
	private void checkAndPaintSelectionIndicator(Graphics g)
	{
		SelectionHandler handler = resources.getSelectionHandler();
		
		if(handler.isSelection(ent))
		{
			Color selectionColor = new Color(0, 0, 255, 120);
			
			g.setColor(selectionColor);
			
			Dimension size = getSize();
			
			g.fillRect(0, 0, size.width, size.height);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) 
	{
		if(SwingUtilities.isLeftMouseButton(e))
		{
			SelectionHandler handler = resources.getSelectionHandler();
			
			if(handler.isSelection(ent))
			{
				handler.removeSelection();
				resources.setTool(resources.SELECT_TOOL);
			}
			else
			{
				resources.setTool(resources.STAMP);
				handler.setSelection(ent, false);
			}
			repaint();
		}
	}	
}
