package Editor.headings;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;

import Editor.EditorResources;
import Editor.selection.SelectionHandler;
import Tileset.TMCell;

public class SelectAllButton extends JButton
	implements ActionListener
{
	private EditorResources resources;
	
	public SelectAllButton(EditorResources resources)
	{
		this.resources = resources;
		
		addActionListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		List<TMCell> cells = resources.scene
									  .getTileMap()
									  .getCells();
		
		SelectionHandler handler = resources.getSelectionHandler();
		
		if(handler.isSelections(cells))
			handler.removeSelection();
		else
			handler.setSelections(cells, false);
	}
}
