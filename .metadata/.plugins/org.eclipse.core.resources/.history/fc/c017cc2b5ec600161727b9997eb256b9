package Editor;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Editor.actions.InsertTileMap;

public class MenuBar extends JMenuBar
{
	public MenuBar(EditorResources resources)
	{
		JMenu fileMenu = new JMenu("File"),
			  insertMenu = new JMenu("Insert");

		JMenuItem importMenu = new JMenu("Import");
		
		add(fileMenu);
		add(insertMenu);
		
		fileMenu.add(importMenu);
		
		insertMenu.add(new InsertTileMap(resources));
	}
}
