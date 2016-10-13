package EditorGUI;

import java.awt.event.MouseEvent;

public interface MouseListener 
	extends java.awt.event.MouseListener
{
	@Override
	public default void mouseClicked(MouseEvent e) {}

	@Override
	public default void mouseEntered(MouseEvent e) {}

	@Override
	public default void mouseExited(MouseEvent e) {}

	@Override
	public default void mousePressed(MouseEvent e) {}

	@Override
	public default void mouseReleased(MouseEvent e) {}
}
