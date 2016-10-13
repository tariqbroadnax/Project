package EditorGUI;

import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MousePositionListener 
	extends MouseMotionAdapter
{
	private Point mouseLoc;
	
	public Point getMouseLocation()
	{
		return mouseLoc;
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		mouseLoc = e.getPoint();
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		mouseLoc = e.getPoint();
	}
}
