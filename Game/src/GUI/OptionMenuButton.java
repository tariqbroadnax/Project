package GUI;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class OptionMenuButton extends MenuButton
{
	private String[] options;

	private Consumer<String> c;
	
	private int curr;
	
	public OptionMenuButton(
			String str, int start, Consumer<String> c,
			String...options) 
	{
		super(str, options[start]);
		
		this.options = options;
		this.c = c;
		curr = start;
	}
	

	public void keyPressed(KeyEvent e) 
	{
		super.keyPressed(e);
		
		int keyCode = e.getKeyCode();
	
		if(keyCode == KeyEvent.VK_LEFT)
		{
			curr--;
			if(curr < 0) curr = options.length - 1;
			setRightText(options[curr]);
			c.accept(options[curr]);
		}
		else if(keyCode == KeyEvent.VK_RIGHT)
		{
			curr++;curr%=options.length;
			setRightText(options[curr]);
			c.accept(options[curr]);
		}
	}


}
