package Game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Window;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

public class GCanvas extends Canvas
	implements ComponentListener
{
	private Scene scene;

	public GCanvas(GameResources resources)
	{
		scene = resources.scene;
		
		setBackground(Color.white);
		setFocusable(true);	
		
		addComponentListener(this);
	}
	
	public void addNotify()
	{
		super.addNotify();
	
		Container parent = getParent();
		while(!(parent instanceof Window))
			parent = parent.getParent();
		
		parent.addComponentListener(this);
	}
	
	@Override
	public void componentHidden(ComponentEvent e) {}

	@Override
	public void componentMoved(ComponentEvent e) {}

	@Override
	public void componentResized(ComponentEvent e) 
	{		
		if(e.getSource() == this)
		{
			Dimension size = getSize();
			
			scene.getLightMap()
				 .setSize(size);
		}
	}

	@Override
	public void componentShown(ComponentEvent e) {
		createBufferStrategy(2);
	}	
}
