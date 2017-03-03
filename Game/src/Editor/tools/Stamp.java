package Editor.tools;

import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import Editor.EditorResources;
import Editor.SnapSettings;
import Editor.edit.AddEntity;
import Editor.layer_selector.SetTile;
import Editor.selection.SelectionHandler;
import EditorGUI.UndoManager;
import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Graphic.Camera;
import Graphic.Graphic;
import Graphic.GraphicsContext;
import Maths.Dimension2D;
import Tileset.Tile;
import Tileset.TileMap;

public class Stamp implements Tool
{
	private EditorResources resources;
	
	private Point mouseLoc;
	
	public Stamp(EditorResources resources)
	{
		this.resources = resources;
	}
	
	@Override
	public void paint(Graphics g) 
	{		
		if(mouseLoc == null)
			return;
		
		SelectionHandler handler = resources.getSelectionHandler();
		
		// stamp only works with one selection
		if(handler.selectionCount() == 1) 
		{
			Camera camera = resources.getCamera();
			
			Point2D.Double normLoc = camera.normalLocation(mouseLoc);
			
			GraphicsContext gc = new GraphicsContext(g, camera);
			
			Object obj = handler.getSelection().get(0);
			
			if(obj instanceof Entity)
			{
				Entity ent = (Entity) obj;
		
				if(resources.tiledMode())
					updateToTileLoc(ent, normLoc);
			
				paintEntity(ent, gc, normLoc);
			}
		}
	}
	
	private void updateToTileLoc(
			Entity ent,
			Point2D.Double loc)
	{
		Dimension2D.Double graphSize = 
				ent.get(GraphicsComponent.class)
				   .getGraphic()
				   .getSize();
		
		SnapSettings settings = resources.getSnapSettings();
		
		double tw = settings.getSnapWidth(),
			   th = settings.getSnapHeight();
	
		if(loc.x > 0) loc.x += tw;
		if(loc.y > 0) loc.y += th;
			
		loc.x = ((int) (loc.x / tw)) * tw - graphSize.width/2;
		loc.y = ((int) (loc.y / th)) * th - graphSize.height/2;
	}
	
	private void paintEntity(
			Entity ent, GraphicsContext gc, 
			Point2D.Double normLoc)
	{
		Graphic graph = (Graphic)
				ent.get(GraphicsComponent.class)
						  .getGraphic()
						  .clone();
				
		graph.setLoc(normLoc);
		
		graph.paint(gc);
	}
	
	private void tryAndStampTile(Tile tile, Point2D.Double normLoc)
	{
		TileMap tm = resources.scene.getTileMap();

		int row = tm.row(normLoc.y),
			col = tm.col(normLoc.x);
		
		if(0 <= row && row < tm.rows() && 
		   0 <= col && col < tm.cols())
		{
			SetTile edit = new SetTile(resources, tm, tile, row,  col);
			UndoManager undoManager = resources.getUndoManager();
			
			edit.invoke();
			
			undoManager.addEdit(edit);
		}
	}
	
	private void stampEntity(Entity ent, Point2D.Double normLoc)
	{
		ent = (Entity)ent.clone();
		
		if(resources.tiledMode())
			updateToTileLoc(ent, normLoc);
		
		ent.setLoc(normLoc);
		
		AddEntity edit = new AddEntity(ent, resources);
		
		edit.invoke();
		
		resources.getUndoManager()
				 .addEdit(edit);
	}
	
	public void mousePressed(MouseEvent e)
	{
		SelectionHandler handler = resources.getSelectionHandler();
		
		// stamp only works with one selection
		if(handler.selectionCount() == 1) 
		{
			Object obj = handler.getSelection().get(0);
			
			Camera camera = resources.getCamera();
			
			Point mouseLoc = e.getPoint();
			Point2D.Double normLoc = camera.normalLocation(mouseLoc);
			
			if(obj instanceof Tile)
			{
				Tile tile = (Tile) obj;
				
				tryAndStampTile(tile, normLoc);
			}
			else if(obj instanceof Entity)
			{
				Entity ent = (Entity)obj;
				
				stampEntity(ent, normLoc);
			}
		}
		else
		{
			
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		mouseLoc = e.getPoint();
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		mouseLoc = null;
	}
}
