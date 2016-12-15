package GUI;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.Duration;
import java.util.Date;

import Game.Updatable;
import Utilities.Time;

public class SwingUpdater implements ActionListener
{
	private Updatable updatable;
	
	private Date lastUpdate;
	
	public SwingUpdater(Updatable updatable)
	{
		this.updatable = updatable;
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(lastUpdate == null)
			initFirstUpdate();
		else
			update();
	}
	
	private void initFirstUpdate()
	{
		lastUpdate = new Date();
	}
	
	private void update()
	{
		Duration delta = 
				Time.between(lastUpdate, new Date());
		
		updatable.update(delta);
		
		lastUpdate = new Date();
	}
}
