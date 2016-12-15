package EditorGUI;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import EditorActions.Actions;

public class FileMenu extends JMenu
{
	private JMenuItem openItem,
					  saveItem,
					  saveAsItem,
					  saveAllItem,
					  refreshItem,
					  importItem,
					  exportItem,
					  propertiesItem,
					  exitItem;
	
	public FileMenu()
	{
		super("File");
		
		openItem = new JMenuItem(Actions.OPEN);
		saveItem = new JMenuItem(Actions.SAVE);
		saveAsItem = new JMenuItem(Actions.SAVE_AS);
		saveAllItem = new JMenuItem(Actions.SAVE_ALL);
		refreshItem = new JMenuItem(Actions.REFRESH);
		importItem = new JMenuItem(Actions.IMPORT);
		exportItem = new JMenuItem(Actions.EXPORT);
		propertiesItem = new JMenuItem(Actions.PROPERTIES);
		exitItem = new JMenuItem(Actions.EXIT);
		
		add(openItem);
		addSeparator();
		add(saveItem);
		add(saveAsItem);
		add(saveAllItem);
		addSeparator();
		add(refreshItem);
		addSeparator();
		add(importItem);
		add(exportItem);
		addSeparator();
		add(propertiesItem);
		addSeparator();
		add(exitItem);
	}
}
