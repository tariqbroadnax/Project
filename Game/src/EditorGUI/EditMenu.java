package EditorGUI;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import EditorActions.Actions;

public class EditMenu extends JMenu
{
	private JMenuItem undoItem,
					  redoItem,
					  cutItem,
					  copyItem,
					  pasteItem,
					  deleteItem;
	
	public EditMenu()
	{
		super("Edit");
		
		undoItem = new JMenuItem(Actions.UNDO);
		redoItem = new JMenuItem(Actions.REDO);
		cutItem = new JMenuItem(Actions.CUT);
		copyItem = new JMenuItem(Actions.COPY);
		pasteItem = new JMenuItem(Actions.PASTE);
		deleteItem = new JMenuItem(Actions.DELETE);
		
		add(undoItem);
		add(redoItem);
		addSeparator();
		add(cutItem);
		add(copyItem);
		add(pasteItem);
		addSeparator();
		add(deleteItem);
		
	}
}
