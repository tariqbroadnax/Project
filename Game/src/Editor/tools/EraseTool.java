package Editor.tools;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import Editor.EditorResources;
import Entity.Entity;
import Graphic.Camera;
import Tileset.TMCell;

public class EraseTool implements Tool
{
	private boolean ignoreTileMap = true;

	private EditorResources resources;
	
	public EraseTool(EditorResources resources)
	{
		this.resources = resources;
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		Camera camera = resources.getCamera();
		
		Point mouseLoc = e.getPoint();
		Point2D.Double normLoc = camera.normalLocation(mouseLoc);
		
		Entity ent = resources.scene.entityAtLoc(normLoc);
		
		if(ent != null)
		{
			resources.scene.removeEntity(ent);
			resources.notifyOfSceneChange();
			return;
		}
		
		TMCell cell = resources.scene
							   .getTileMap()
							   .cellAtLoc(normLoc);
		
		if(cell != null)
		{
			cell.set(null);
			resources.notifyOfSceneChange();
		}
		
	}
}