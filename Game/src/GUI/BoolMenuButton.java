package GUI;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class BoolMenuButton extends MenuButton
{
	private boolean curr;
	
	private Consumer<Boolean> ft;
	
	public BoolMenuButton(String str, boolean init,
			Consumer<Boolean> ft) 
	{
		super(str, "" + init);
		curr = init;
		this.ft = ft;
 	}
	
	
	public void keyPressed(KeyEvent e) 
	{
		super.keyPressed(e);
		
		int keyCode = e.getKeyCode();
	
		if(keyCode == KeyEvent.VK_LEFT ||
		   keyCode == KeyEvent.VK_RIGHT)
		{
			curr = !curr;
			setRightText(""+curr);
			ft.accept(curr);
		}
	}

}
