package Editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import Tileset.Tileset;

public class Selector extends JPanel
{	
	private JTabbedPane tabbedPane;
	
	private Dimension prefSize;
	
	private EditorResources resources;
	
	private List<TileSelectorPanel> tileSelectors;
	
	public Selector(EditorResources resources)
	{
		this.resources = resources;
		
		tabbedPane = new JTabbedPane();
		
		prefSize = new Dimension(200, 600);
		
		tileSelectors = new ArrayList<TileSelectorPanel>();
		
		setLayout(new BorderLayout());
		add(tabbedPane, BorderLayout.CENTER);
	
		setPreferredSize(prefSize);
		
		restoreTilesets();
	}
	
	private void restoreTilesets()
	{
		List<Tileset> tilesets = resources.getState()
			  	  .getOpenTilesets();
		
		for(Tileset ts : tilesets)
			open(ts);
	}
	
	public void open(Tileset tileset)
	{
		TileSelectorPanel selector = 
				new TileSelectorPanel(resources, tileset);

		String fileName = tileset.getFile()
								 .getName();

		tabbedPane.add(fileName, selector);
		tileSelectors.add(selector);
		
		int i = tabbedPane.getTabCount() - 1;
		SelectorTabComponent comp = 
				new SelectorTabComponent(tabbedPane, this);
		
		tabbedPane.setTabComponentAt(i, comp);
		
		resources.getState()
				 .notifyOfOpenTileset(tileset);
	}
	
	public void close(Tileset tileset)
	{
		for(int i = 0; i < tabbedPane.getTabCount(); i++)
		{
			TileSelectorPanel tselector = tileSelectors.get(i);
			
			if(tselector.getTileset() == tileset)
			{
				tabbedPane.remove(tselector);
				tileSelectors.remove(tselector);
				resources.getState()
						 .notifyOfClosedTileset(tileset);
			}
		}
	}
	
	public TileSelectorPanel getTileSelector(int i )
	{
		return tileSelectors.get(i);
	}
}
