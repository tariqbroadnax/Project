package Actions;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

import Game.GameResources;

public abstract class SyncGameAction extends GameAction
{
	public SyncGameAction(GameResources resources) {
		super(resources);
	}

	public abstract void invoke();
	
	@Override
	public void actionPerformed(ActionEvent e) {
		resources.buffer.addAction(this);
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		resources.buffer.addAction(this);
	}
}
