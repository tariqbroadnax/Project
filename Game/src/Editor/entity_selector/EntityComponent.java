package Editor.entity_selector;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import Editor.EditorResources;
import Editor.selection.SelectionHandler;
import Editor.selection.SelectionListener;
import EditorGUI.MouseListener;
import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Graphic.Camera;
import Graphic.Graphic;
import Graphic.GraphicsContext;
import Maths.Dimension2D;

public class EntityComponent extends JPanel
	implements MouseListener, SelectionListener,
			   FocusListener
{
	private EditorResources resources;
	
	private Entity ent;
	
	public EntityComponent(EditorResources resources, Entity ent)
	{
		this.resources = resources;
		this.ent = ent;
		
		resources.getSelectionHandler()
				 .addSelectionListener(this);
		
		addMouseListener(this);
		addFocusListener(this);
		
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
		
		revalidate();
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

			Dimension size = getSize();
			
			Point2D.Double graphLoc = graph.getLoc();
			
			Dimension2D.Double normSize = camera.normalSize(size);
			
			camera.setFocusTL(graphLoc.x  - normSize.width/2,
							  graphLoc.y - normSize.height/2);
			
			GraphicsContext gc = new GraphicsContext(g, camera);
	
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
	
	private void updateSelection()
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
	
	@Override
	public void mousePressed(MouseEvent e) 
	{					
		if(SwingUtilities.isLeftMouseButton(e))
		{
			requestFocusInWindow();

			boolean focused = hasFocus();
			
			if(focused)
				updateSelection();
		}	
	}

	@Override
	public void selectionChanged() {
		repaint();
	}	
	
	public void selectionModified() {
		SelectionHandler handler = resources.getSelectionHandler();
		
		if(handler.isSelection(ent))
			updateSize();
		
		repaint();
	}

	@Override
	public void focusGained(FocusEvent e) {
		updateSelection();
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
