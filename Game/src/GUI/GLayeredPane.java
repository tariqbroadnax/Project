package GUI;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Component;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JLayeredPane;

public class GLayeredPane extends JLayeredPane
	implements ComponentListener
{	
	public GLayeredPane()
	{
		setLayout(new BorderLayout());
		
		addComponentListener(this);
	}
	
	public void addAndSetLayer(Component comp, int index)
	{
		add(comp);
		setLayer(comp, index);
	}

	public void componentHidden(ComponentEvent e){}
	public void componentMoved(ComponentEvent e){}
	public void componentShown(ComponentEvent e){}

	public void componentResized(ComponentEvent e)
	{
		for(Component comp : getComponents())
			comp.setSize(getSize());
		
		// every comp added should fill the area
	}
}
