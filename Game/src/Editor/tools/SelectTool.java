package Editor.tools;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.SwingUtilities;

import Editor.EditorResources;
import Editor.SnapSettings;
import Editor.selection.SelectionHandler;
import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Game.Scene;
import Graphic.Camera;
import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;
import Maths.Dimension2D;
import Maths.Geometry;

public class SelectTool implements Tool
{	
	private EditorResources resources;
	
	private boolean entSelection = false,
				 	boundSelection = false;
	
	private List<Point2D.Double> startLocs;
	private Point2D.Double loc;
	private Entity ent;
	private boolean entMoved;
	
	private Point corner1, corner2;
	private Rectangle2D.Double selectBound;
	
	public SelectTool(EditorResources resources)
	{
		this.resources = resources;
		
		startLocs = new ArrayList<Point2D.Double>();
	}
	
	@Override
	public void paint(Graphics g)
	{
		if(boundSelection)
			paintSelectionIndicator(g);
	}
		
	private void paintSelectionIndicator(Graphics g)
	{
		Camera camera = resources.getCamera();
		
		GraphicsContext gc = new GraphicsContext(g, camera);

		ShapeGraphic graph = new ShapeGraphic();	
		
		graph.setShape(selectBound);
		
		graph.setFilled(false);
		graph.setPaint(Color.blue);
		
		graph.paint(gc);
		
		graph.setFilled(true);
		graph.setPaint(new Color(0, 0, 255, 120));
		
		graph.paint(gc);
	}
	
	private void updateSelectionBound()
	{
		Camera camera = resources.getCamera();

		Point2D.Double normCorner1 = camera.normalLocation(corner1),
					   normCorner2 = camera.normalLocation(corner2);
		
		selectBound = Geometry.rectangle(normCorner1, normCorner2);
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{				
		if(!SwingUtilities.isLeftMouseButton(e))
			return;
		
		Camera camera = resources.getCamera();
		
		Point mouseLoc = e.getPoint();
		Point2D.Double normLoc = camera.normalLocation(mouseLoc);
	
		SelectionHandler handler = resources.getSelectionHandler();
		
		ent = resources.scene.entityAtLoc(normLoc);
				
		if(ent != null)
		{
			entSelection = true;
				
			List<Object> selection = handler.getSelection();
			
			if(!selection.contains(ent))
				handler.setSelection(ent, true);
			
			selection = handler.getSelection();
			
			loc = normLoc;
			
			startLocs.clear();
			for(Object obj : selection)
			{
				Entity ent = (Entity) obj;
				Point2D.Double loc = (Point2D.Double) ent.getLoc()
														 .clone();
				
				startLocs.add(loc);
			}
		}
		else
		{			
			boundSelection = true;
			corner1 = corner2 = e.getPoint();
			
			updateSelectionBound();
		}
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if(entSelection)
		{
			entMoved = true;
		
			Camera camera = resources.getCamera();
			
			Point p = e.getPoint();
			Point2D.Double normLoc = camera.normalLocation(p);
			
			double xOffset = -(loc.x - normLoc.x),
				   yOffset = -(loc.y - normLoc.y);
						
			List<Object> selection = resources.getSelectionHandler()
											  .getSelection();
			
			Iterator<Point2D.Double> iter = startLocs.iterator();
			
			for(Object obj : selection)
			{
				Entity ent = (Entity) obj;
				
				Point2D.Double loc = iter.next();
				
				if(resources.tiledMode())
				{
					SnapSettings settings = resources.getSnapSettings();
					
					loc = settings.snapLoc(loc.x + xOffset,
										   loc.y + yOffset);
					
					ent.setLoc(loc);
				}
				else
					ent.setLoc(loc.x + xOffset, loc.y + yOffset);
			}
			
			
		}
		else if(boundSelection)
		{
			corner2 = e.getPoint();
	
			updateSelectionBound();
			
			selectEntities();
		}

	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(entSelection)
		{
			if(!entMoved)
			{
				SelectionHandler handler = 
						resources.getSelectionHandler();

				handler.setSelection(ent, true);
			}
		}
		else if(boundSelection)
			selectEntities();
		
		entSelection = boundSelection = entMoved = false;
	}
	
	private void selectEntities()
	{
		Scene scene = resources.scene;
		
		List<Entity> entities = 
				scene.entitiesVisibleInsideBound(selectBound);
	
		SelectionHandler handler = resources.getSelectionHandler();
	
		if(!entities.isEmpty())
			handler.setSelections(entities, true);
		else
			handler.removeSelection();
	}
}
