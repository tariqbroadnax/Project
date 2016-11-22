package Actions;

import javax.swing.AbstractAction;

import EditorGUI.MouseListener;
import Game.GameResources;

public abstract class GameAction extends AbstractAction
	implements MouseListener
{		
	protected GameResources resources;
	
	public GameAction(GameResources resources) {
		this.resources = resources;
	}
}
