package Actions;

import java.awt.event.ActionEvent;

import Game.Resources;

public abstract class SyncGameAction extends GameAction 
{
	public SyncGameAction(Resources resources) 
	{
		super(resources);
	}

	protected abstract void invoke();
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		resources.getActionBuffer()
				 .addAction(this);
	}
	
}
