package EditorGUI;

import java.io.File;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.filechooser.FileNameExtensionFilter;

import Commands.Redo;
import Commands.Undo;
import EditorMenuBar.RedoItem;
import EditorMenuBar.UndoItem;

public class EditorFrame extends JFrame
{
	private JMenuBar menuBar;
	
	private JMenu fileMenu,
				  editMenu;
		
	private JMenuItem openItem,
					  closeItem,
					  saveItem,
					  saveAsItem,
					  undoItem,
					  redoItem;
	
	private JMenu newSubMenu,
				  importSubMenu;

	private JMenuItem newSceneItem,
					  newEntityItem,
					  importTileItem;
	
	
	private GUIResources resources;
		
	public EditorFrame(GUIResources resources)
	{
		super("Real Fiction Editor");
	
		this.resources = resources;
				
		menuBar = new JMenuBar();
		
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		
		newSubMenu = new JMenu("New");
		importSubMenu = new JMenu("Import");
		
		importTileItem = new JMenuItem("Tile");
		
		newSceneItem = new JMenuItem("Scene");
		newEntityItem = new JMenuItem("Entity");
		openItem = new JMenuItem("Open");
		closeItem = new JMenuItem("Close");
		undoItem = new UndoItem(resources);
		redoItem = new RedoItem(resources);
		saveItem = new JMenuItem("Save");
		saveAsItem = new JMenuItem("Save As");
		
		addComponents();
		addListeners();
		
		setJMenuBar(menuBar);
	
	    JPanel content = (JPanel) getContentPane();

		InputMap inputMap = content.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap actionMap = content.getActionMap();
		
		Undo undo = new Undo(resources);
		Redo redo = new Redo(resources);
		
		inputMap.put(KeyStroke.getKeyStroke("control X"), undo);
		inputMap.put(KeyStroke.getKeyStroke("control Y"), redo);
		
		actionMap.put(undo, undo);
		actionMap.put(redo, redo);
	}
	
	private void addComponents()
	{
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		
		JMenuItem[] items = 
		{
			newSubMenu, openItem, closeItem,
			saveItem, saveAsItem, importSubMenu
		};
		
		for(JMenuItem item : items)
			fileMenu.add(item);
	
		addAll(newSubMenu,
			   newSceneItem, newEntityItem);
	
		addAll(editMenu,
			   undoItem, redoItem);
		
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
		
		openItem.addActionListener(
				e -> openScene());
		closeItem.addActionListener(
				e -> close());
		
		saveItem.addActionListener(
				e -> resources.saveScene());
		saveAsItem.addActionListener(
				e -> resources.saveSceneAs());
		
		importTileItem.addActionListener(
				e -> new ImportTileDialog(resources)
					 .setVisible(true));
	
		Redo redo = new Redo(resources);		

		redoItem.addActionListener(redo);
	}
	
	private void openScene()
	{
		if(!resources.sceneShouldBeChanged())
			return;
		
		JFileChooser chooser = 
				new JFileChooser();
		
		chooser.setFileSelectionMode(
				JFileChooser.FILES_ONLY);
		
	    FileNameExtensionFilter filter = 
	    		new FileNameExtensionFilter(
	    				"Scene Files", "scn");
	    
	    chooser.setFileFilter(filter);
	    
	    int returnVal = chooser.showOpenDialog(
	    		resources.frame);

	    if(returnVal == JFileChooser.APPROVE_OPTION)
	    {
	    	File file = chooser.getSelectedFile();
	    	
	    	resources.loadScene(file.toPath());
	    }
	}
	
	private void close()
	{	
		if(resources.sceneShouldBeChanged())
			System.exit(1);
	}
}
