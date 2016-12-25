package Editor;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Editor.actions.Copy;
import Editor.actions.Cut;
import Editor.actions.Import;
import Editor.actions.InsertTileMap;
import Editor.actions.New;
import Editor.actions.Open;
import Editor.actions.Paste;
import Editor.actions.Redo;
import Editor.actions.Save;
import Editor.actions.SaveAs;
import Editor.actions.Undo;

public class MenuBar extends JMenuBar
{
	public MenuBar(EditorResources resources)
	{
		JMenu fileMenu = new JMenu("File"),
			  editMenu = new JMenu("Edit"),
			  playMenu = new JMenu("Play"),
			  viewMenu = new JMenu("View"),
			  insertMenu = new JMenu("Insert");

		JMenuItem importMenu = new JMenu("Import");
		
		add(fileMenu);
		add(editMenu);
		add(viewMenu);
		add(playMenu);
		
		//add(insertMenu);
		
		//fileMenu.add(importMenu);
		fileMenu.add(new New(resources));
		fileMenu.add(new Open(resources));
		//fileMenu.add(new Close());
		fileMenu.add(new Save(resources));
		fileMenu.add(new SaveAs(resources));
		fileMenu.add(new Import());
		
		editMenu.add(resources.getUndoAction());
		editMenu.add(resources.getRedoAction());
		editMenu.add(new Cut());
		editMenu.add(new Copy());
		editMenu.add(new Paste());
		
		insertMenu.add(new InsertTileMap(resources));
	}
}
