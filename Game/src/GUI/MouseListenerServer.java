package GUI;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class MouseListenerServer 
	implements MouseListener
{
	private MouseListener list;
	
	public void setMouseListener(
			MouseListener list) 
	{
		this.list = list;
	}
	
	public void removeMouseListener() {
		list = null;
	}
	
	@Override
	public void mouseClicked(MouseEvent e) 
	{
		if(list != null)
			list.mouseClicked(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		if(list != null)
			list.mouseEntered(e);
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		if(list != null)
			list.mouseExited(e);
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		if(list != null)
			list.mousePressed(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		if(list != null)
			list.mouseReleased(e);
	}
}
