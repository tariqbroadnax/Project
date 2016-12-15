package Editor;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

import Tileset.Tileset;

public class SelectorTabComponent extends ButtonTabComponent
	implements ActionListener
{
	private final JTabbedPane pane;
	
	private final Selector selector;
	
	public SelectorTabComponent(
			final JTabbedPane pane,
			final Selector selector) 
	{
		super(pane);
		
		if(selector == null)
			throw new NullPointerException("selector is null");
		
		this.pane = pane;
		this.selector = selector;
		
		getButton().addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int i = pane.indexOfTabComponent(this);
		
		TileSelectorPanel tileSelector = 
				selector.getTileSelector(i);
	
		Tileset ts = tileSelector.getTileset();
		
		selector.close(ts);
	}
	
}
