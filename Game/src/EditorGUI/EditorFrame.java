package EditorGUI;

import java.awt.BorderLayout;
import java.awt.event.KeyEvent;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;

import EditorActions.Actions;
import EditorActions.Exit;
import EditorActions.Open;
import EditorActions.Redo;
import EditorActions.Save;
import EditorActions.SaveAs;
import EditorActions.Undo;
import EditorMenuBar.RedoItem;
import EditorMenuBar.UndoItem;
import ToolBar.NewFileToolBarButton;
import ToolBar.SaveToolBarButton;

public class EditorFrame extends JFrame
{
	private JMenuBar menuBar;
	
	private EditMenu editMenu;
	private FileMenu fileMenu;
		
	private JMenuItem openItem,
					  exitItem,
					  saveItem,
					  saveAsItem,
					  undoItem,
					  redoItem;
	
	private JMenu newSubMenu,
				  importSubMenu;

	private JMenuItem newSceneItem,
					  newEntityItem,
					  importTileItem;
	
	
	private JToolBar toolBar;
	
	private GUIResources resources;
		
	public EditorFrame(GUIResources resources)
	{
		super("Real Fiction Editor");
	
		this.resources = resources;
	
		menuBar = new JMenuBar();
		
		fileMenu = new FileMenu();
		editMenu = new EditMenu();
		
		newSubMenu = new JMenu("New");
		importSubMenu = new JMenu("Import");
		
		importTileItem = new JMenuItem("Tile");
		
		newSceneItem = new JMenuItem("Scene");
		newEntityItem = new JMenuItem("Entity");
		openItem = new JMenuItem(Actions.OPEN);
		exitItem = new JMenuItem(Actions.EXIT);
		undoItem = new JMenuItem(Actions.UNDO);
		redoItem = new JMenuItem(Actions.REDO);
		saveItem = new JMenuItem(Actions.SAVE);
		saveAsItem = new JMenuItem(Actions.SAVE_AS);
		
		fileMenu.setMnemonic('f');
		
		toolBar = new JToolBar("TESTING TESTING");
		
		addComponents();
		addListeners();
		
		setJMenuBar(menuBar);
	
		toolBar.add(new NewFileToolBarButton());
		toolBar.add(new SaveToolBarButton());
		
	    JPanel content = (JPanel)getContentPane();
	    
	    content.setLayout(new BorderLayout());
	    
	    content.add(toolBar, BorderLayout.NORTH);
	}
	
	private void addComponents()
	{
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
	
		addAll(newSubMenu,
			   newSceneItem, newEntityItem);
		
		importSubMenu.add(importTileItem);
	}
	
	private void addAll(JMenu menu, JMenuItem...items)
	{
		for(JMenuItem item : items)
			menu.add(item);
	}
	
	private void addListeners()
	{
		newSceneItem.addActionListener(
				e -> resources.createNewScene());
		
		saveAsItem.addActionListener(
				e -> resources.saveSceneAs());
		
		importTileItem.addActionListener(
				e -> new ImportTileDialog(resources)
					 .setVisible(true));
	}
}
