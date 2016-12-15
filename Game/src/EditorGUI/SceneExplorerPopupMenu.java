package EditorGUI;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import EditorActions.Refresh;

public class SceneExplorerPopupMenu 
	extends JPopupMenu
{
	private JMenuItem refreshItem;
	
	public SceneExplorerPopupMenu()
	{
		refreshItem = new JMenuItem(new Refresh());
		
		add(refreshItem);
	}
}
