package Editor;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import Tileset.Tileset;

public class EditorMenuBar extends JMenuBar
{
	private JMenu fileMenu,
				  newMenu;
	
	private JMenuItem tsMI;
	
	private TilesetChooser tsChooser;
	
	private Selector selector;
	
	public EditorMenuBar(Selector selector)
	{
		this.selector = selector;
		
		fileMenu = new JMenu("File");
		newMenu = new JMenu("New");
		
		tsMI = new JMenuItem("Tileset");
		
		tsChooser = new TilesetChooser();
		
		add(fileMenu);
		
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
