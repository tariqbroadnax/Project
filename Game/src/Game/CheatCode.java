package Game;

import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

public class CheatCode implements Updatable, KeyListener
{
	private List<Integer> keyCodes;
	
	private int i;
	
	private long idleQuota, elapsed;
	
	public CheatCode()
	{
		keyCodes = new ArrayList<Integer>();
		
		idleQuota = 500;
		
		elapsed = i = 0;
		
		keyCodes.add(38);
		keyCodes.add(38);
		keyCodes.add(37);
	}
	
	public void update(Duration delta)
	{
		elapsed += delta.toMillis();
		
		if(elapsed > idleQuota)
			elapsed = i = 0;
		
		if(!keyCodes.isEmpty() && i == keyCodes.size())
		{
			invoke();
			i = 0;
		}
	}
	
	private void invoke()
	{
		System.out.println("Game.CheatCode: Cheat Code Activated!");
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(!(keyCodes.isEmpty() || i == keyCodes.size()))
		{
			int keyCode = e.getKeyCode(),
				targetKeyCode = keyCodes.get(i);
						
			if(keyCode == targetKeyCode)
			{
				elapsed = 0;
				i++;
			}
			else
				elapsed = i = 0;
		}
	}
}
