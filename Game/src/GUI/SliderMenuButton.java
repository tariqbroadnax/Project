package GUI;

import java.awt.event.KeyEvent;
import java.util.function.Consumer;

public class SliderMenuButton extends MenuButton
{
	private int curr, offset, min, max;
	
	private Consumer<Integer> c;
	
	public SliderMenuButton(String str, int start, int offset,
			int min, int max, Consumer<Integer> c) 
	{
		super(str, "" + start);
	
		curr = start;
		this.offset = offset;
		this.min = min;
		this.max = max;
		this.c = c;
	}
	
	public void keyPressed(KeyEvent e) 
	{
		super.keyPressed(e);
		
		int keyCode = e.getKeyCode();
	
		if(keyCode == KeyEvent.VK_LEFT &&
		   curr > min)
		{
			curr -= offset;
			setRightText(""+curr);
			c.accept(curr);
		}
		else if(keyCode == KeyEvent.VK_RIGHT &&
			    curr < max)
		{
			curr += offset;
			setRightText(""+curr);
			c.accept(curr);
		}
		else if(keyCode == KeyEvent.VK_ESCAPE)
		{
			((DebugPanel)getParent().getParent())
			.pop();
		}
	}


}
