package GUI;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public class KeepFocus implements FocusListener
{
	@Override
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent e) 
	{
		e.getComponent().requestFocusInWindow();
	}

}
