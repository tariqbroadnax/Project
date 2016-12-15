package GUI;

import java.awt.event.KeyEvent;

public class NavMenuButton extends MenuButton
{	
	private DebugMenu menu;

	public NavMenuButton(
			String str, DebugMenu menu) 
	{
		super(str, "");
	
		this.menu = menu;	
	}
	
	public void keyPressed(KeyEvent e) 
	{
		super.keyPressed(e);
		
		int keyCode = e.getKeyCode();
		if(keyCode == KeyEvent.VK_ENTER ||
		   keyCode == KeyEvent.VK_RIGHT)
		{
			((DebugPanel)getParent().getParent())
			.push(menu);
		}
	}

}
