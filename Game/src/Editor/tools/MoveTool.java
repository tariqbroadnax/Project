package Editor.tools;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;

import Editor.EditorResources;
import Graphic.Camera;
import Maths.Vector2D;

public class MoveTool implements Tool
{
	private EditorResources resources;
	
	private Point start;
	private Point2D.Double focus;
	
	public MoveTool(EditorResources resources)
	{
		this.resources = resources;
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		Camera camera = resources.getCamera();

		focus = (Point2D.Double) camera.getFocus()
									   .clone();
		
		start = e.getPoint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		Camera camera = resources.getCamera();
	
		Point end = e.getPoint();
		
		int dx = start.x - end.x,
			dy = start.y - end.y;
		
		Vector2D.Double dv = camera.normalVector(dx, dy);
				
		camera.setFocus(focus.x + dv.x, focus.y + dv.y);
	}
}
