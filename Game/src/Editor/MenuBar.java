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
import Editor.actions.OpenSnapSettings;
import Editor.actions.Paste;
import Editor.actions.Save;
import Editor.actions.SaveAs;
import Editor.actions.SetEraseTool;
import Editor.actions.SetMoveTool;
import Editor.actions.SetSelectTool;
import Editor.actions.SetStampTool;

public class MenuBar extends JMenuBar
{
	public MenuBar(EditorResources resources)
	{
		JMenu fileMenu = new JMenu("File"),
			  editMenu = new JMenu("Edit"),
			  playMenu = new JMenu("Play"),
			  viewMenu = new JMenu("View"),
			  toolMenu = new JMenu("Tool"),
			  insertMenu = new JMenu("Insert");

		JMenuItem importMenu = new JMenu("Import");
				
		add(fileMenu);
		add(editMenu);
		add(viewMenu);
		add(toolMenu);
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
		editMenu.add(new Copy());
		editMenu.add(new Cut());
		editMenu.add(new Paste());
		
		editMenu.addSeparator();
		editMenu.add(new OpenSnapSettings(resources));
		
		//insertMenu.add(new InsertTileMap(resources));
	
		toolMenu.add(new SetSelectTool(resources));
		toolMenu.add(new SetStampTool(resources));
		toolMenu.add(new SetEraseTool(resources));
		toolMenu.add(new SetMoveTool(resources));
	}
}
