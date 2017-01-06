package Editor;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Editor.tile.TilesetChooser;
import Tileset.Tileset;

public class EditorMenuBar extends JMenuBar
{
	private JMenu fileMenu, editMenu, playMenu,
				  newMenu;
	
	private JMenuItem tsMI;
	
	private TilesetChooser tsChooser;
	
	private Selector selector;
	
	public EditorMenuBar(Selector selector)
	{
		this.selector = selector;
		
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		playMenu = new JMenu("Play");
		
		newMenu = new JMenu("New");
		
		tsMI = new JMenuItem("Tileset");
		
		tsChooser = new TilesetChooser();
		
		add(fileMenu);
		add(editMenu);
		add(playMenu);
		
		fileMenu.add(newMenu);
		
		newMenu.add(tsMI);
	
		tsMI.addActionListener(e -> openTileset());
	}
	
	private void openTileset()
	{
		ReturnOption option = 
				tsChooser.showOpenDialog();
		
		if(option == ReturnOption.APPROVE)
		{
			Tileset tileset = tsChooser.getSelectedTileset();
			
			selector.open(tileset);
		}
	}
}
