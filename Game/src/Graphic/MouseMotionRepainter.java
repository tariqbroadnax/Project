package Graphic;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class MouseMotionRepainter extends
	MouseMotionAdapter
{
	private Component comp;
	
	public MouseMotionRepainter(Component comp)
	{
		this.comp = comp;	
	}
	
	@Override
	public void mouseMoved(MouseEvent e) 
	{
		comp.repaint();
	}

}
