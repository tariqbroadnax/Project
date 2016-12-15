package EditorGUI;

import java.awt.event.MouseEvent;

public class MouseEventDispatcher 
	implements java.awt.event.MouseListener,
			   java.awt.event.MouseMotionListener
{
	private void dispatch(MouseEvent e) {
		e.getComponent()
		 .getParent()
		 .dispatchEvent(e);
	}
	
	@Override
	public void mouseDragged(MouseEvent e) {
		dispatch(e);
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		dispatch(e);
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		dispatch(e);		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		dispatch(e);
	}

	@Override
	public void mouseExited(MouseEvent e) {
		dispatch(e);
	}

	@Override
	public void mousePressed(MouseEvent e) {
		dispatch(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		dispatch(e);
	}

}
