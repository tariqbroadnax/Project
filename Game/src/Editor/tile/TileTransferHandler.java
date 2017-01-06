package Editor.tile;

import java.awt.Color;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.SwingUtilities;

import Editor.EditorResources;
import Editor.SelectionTransferHandler;
import Editor.layer_selector.RemoveTile;
import Editor.layer_selector.SetTile;
import Editor.selection.SelectionHandler;
import EditorGUI.UndoManager;
import Graphic.Camera;
import Graphic.GraphicsContext;
import Graphic.ShapeGraphic;
import Maths.Dimension2D;
import Tileset.Tile;
import Tileset.TileMap;

public class TileTransferHandler 
	extends SelectionTransferHandler
{
	private Tile tile;
	
	private EditorResources resources;
			
	private Camera camera;
		
	private Point mouseLoc;
	
	public TileTransferHandler(
			EditorResources resources,
			Camera camera) 
	{	
		this.resources = resources;
		this.camera = camera;
	}

	@Override
	public boolean setSelection() 
	{
		SelectionHandler handler = resources.getSelectionHandler();
		
		if(handler.instanceSelection(Tile.class))
		{
			List<Object> selection = handler.getSelection();
			
			if(selection.size() == 1)
			{
				tile = (Tile) selection.get(0);
				return true;
			}
		}
		
		return false;
	}

	private void importSelection() 
	{
		Point2D.Double normLoc = camera.normalLocation(mouseLoc);

		TileMap tm = resources.scene.getTileMap();

		int row = tm.row(normLoc.y),
			col = tm.col(normLoc.x);
		
		if(0 <= row && row < tm.rows && 
		   0 <= col && col < tm.cols)
		{
			SetTile edit = new SetTile(resources, tm, tile, row,  col);
			UndoManager undoManager = resources.getUndoManager();
			
			edit.invoke();
			
			undoManager.addEdit(edit);
		}
	}
	
	private void removeTile()
	{
		Point2D.Double normLoc = camera.normalLocation(mouseLoc);

		TileMap tm = resources.scene.getTileMap();

		int row = tm.row(normLoc.y),
			col = tm.col(normLoc.x);
		
		if(0 <= row && row < tm.rows && 
		   0 <= col && col < tm.cols)
		{
			RemoveTile edit = new RemoveTile(resources, tm, row,  col);
			UndoManager undoManager = resources.getUndoManager();
			
			edit.invoke();
			undoManager.addEdit(edit);
		}
	}

	@Override
	public void paintSelection(GraphicsContext gc) 
	{				
		Point2D.Double normLoc = camera.normalLocation(mouseLoc);
		
		TileMap tm = resources.scene.getTileMap();
		
		int row = tm.row(normLoc.y),
			col = tm.col(normLoc.x);
			
			if(!(0 <= row && row < tm.rows && 
			     0 <= col && col < tm.cols))
				return;
			
			double x = tm.x(col), y = tm.y(row);

			Dimension2D.Double tileSize = tm.tileSize();
		
			Rectangle2D.Double tileBound =
					new Rectangle2D.Double(
							0, 0, tileSize.width, 
								  tileSize.height);
		
			ShapeGraphic shape = new ShapeGraphic();
			
			shape.setLoc(x + tileSize.width/2, 
						 y + tileSize.height/2);
			
			shape.setShape(tileBound);
			
			Color selectionColor = new Color(0, 0, 255, 120);
			shape.setPaint(selectionColor);
			
			shape.paint(gc);
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		mouseLoc = e.getPoint();

		if(SwingUtilities.isLeftMouseButton(e))
			importSelection();
		else if(SwingUtilities.isRightMouseButton(e))
			removeTile();
	}
	
	public void mouseMoved(MouseEvent e)
	{
		mouseLoc = e.getPoint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		mouseLoc = e.getPoint();

		if(SwingUtilities.isLeftMouseButton(e))
			importSelection();
		else if(SwingUtilities.isRightMouseButton(e))
			removeTile();
	}
}