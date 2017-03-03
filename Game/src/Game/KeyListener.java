package Game;

import java.awt.event.KeyEvent;

public interface KeyListener extends java.awt.event.KeyListener
{
	@Override
	public default void keyPressed(KeyEvent e) {}

	@Override
	public default void keyReleased(KeyEvent e) {}

	@Override
	public default void keyTyped(KeyEvent e) {} 
}
