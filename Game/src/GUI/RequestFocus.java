package GUI;

import java.awt.Component;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class RequestFocus extends WindowAdapter
{
	private Component comp;
	
	public RequestFocus(Component comp)
	{
		this.comp = comp;
	}
	
	public void windowGainedFocus(WindowEvent e) 
	{
        comp.requestFocusInWindow();
    }
}
