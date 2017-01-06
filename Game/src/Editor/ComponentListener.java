package Editor;

import java.awt.event.ComponentEvent;

public interface ComponentListener extends java.awt.event.ComponentListener
{
	public default void componentHidden(ComponentEvent e) {}
	public default void componentMoved(ComponentEvent e) {}
	public default void componentResized(ComponentEvent e) {}
	public default void componentShown(ComponentEvent e) {}

}
