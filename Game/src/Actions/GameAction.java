package Actions;

import javax.swing.AbstractAction;

import Game.Resources;

public abstract class GameAction extends AbstractAction
{
	protected Resources resources;
		
	public GameAction(Resources resources)
	{
		super();
		
		this.resources = resources;
	}
}
