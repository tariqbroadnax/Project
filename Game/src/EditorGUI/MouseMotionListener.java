package EditorGUI;

import java.awt.event.MouseEvent;

public interface MouseMotionListener
	extends java.awt.event.MouseMotionListener
{
	@Override
	public default void mouseDragged(MouseEvent e) {}

	@Override
	public default void mouseMoved(MouseEvent e) {}

}
