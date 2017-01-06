package Editor.tools;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.List;

import Editor.EditorResources;
import Editor.selection.SelectionHandler;
import Entity.Entity;
import Game.Scene;
import Graphic.Camera;
import Tileset.TMCell;
import Tileset.TileMap;

public class SelectTool implements Tool
{
	private boolean ignoreTileMap = true;
	
	private EditorResources resources;
	
	public SelectTool(EditorResources resources)
	{
		this.resources = resources;
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		Camera camera = resources.getCamera();
		
		Point mouseLoc = e.getPoint();
		Point2D.Double normLoc = camera.normalLocation(mouseLoc);
	
		SelectionHandler handler = resources.getSelectionHandler();
		
		Entity ent = resources.scene.entityAtLoc(normLoc);
		
		if(ent != null)
		{
			handler.setSelection(ent, true);
			return;
		}
		
		TMCell cell = resources.scene
							   .getTileMap()
							   .cellAtLoc(normLoc);
		
		if(!ignoreTileMap && cell != null)
			handler.setSelection(cell, true);
		else
			handler.removeSelection();
	}
}
