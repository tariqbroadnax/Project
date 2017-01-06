package Editor.tile;

import java.awt.BorderLayout;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Editor.EditorResources;
import Tileset.Tileset;

public class TileSelectorPanel extends JPanel
{
	private Tileset tileset;
	
	public TileSelectorPanel(
			EditorResources resources,
			Tileset tileset)
	{
		this.tileset = tileset;
		
		TileSelector selector = new TileSelector(
				resources, tileset);
		
		JScrollPane scrollPane = new JScrollPane(selector);
				
		TSToolbar toolbar = new TSToolbar(selector);
		
		setLayout(new BorderLayout());
		
		add(toolbar, BorderLayout.NORTH);
		add(scrollPane, BorderLayout.CENTER);
	
	}
	
	public Tileset getTileset() {
		return tileset;
	}
}
