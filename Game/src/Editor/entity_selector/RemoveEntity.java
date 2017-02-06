package Editor.entity_selector;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Editor.EditorAssets;
import Editor.EditorResources;
import Editor.selection.SelectionHandler;
import Editor.selection.SelectionListener;
import Entity.Entity;
import Utilities.GUIUtils;

public class RemoveEntity extends AbstractAction
	implements SelectionListener
{
	private EditorResources resources;
	
	public RemoveEntity(EditorResources resources)
	{
		this.resources = resources;	
	
		ImageIcon icon = GUIUtils.ImageIcon("remove.gif");
		
		putValue(SMALL_ICON, icon);
		putValue(LARGE_ICON_KEY, icon);	
		
		SelectionHandler handler = resources.getSelectionHandler();

		handler.addSelectionListener(this);

		checkAndEnable();
	}
	
	private void checkAndEnable()
	{
		SelectionHandler handler = resources.getSelectionHandler();
		
		if(!handler.sceneSelection() && 
			handler.instanceSelection(Entity.class))
			setEnabled(true);
		else
			setEnabled(false);
	}
	
	@Override
	public void actionPerformed(ActionEvent e)
	{
		SelectionHandler handler = resources.getSelectionHandler();
		EditorAssets assets = resources.getEditorAssets();
		
		for(Object obj : handler.getSelection())
		{
			Entity ent = (Entity) obj;
			
			assets.removeEntity(ent);
		}
		
		handler.removeSelection();
	}

	@Override
	public void selectionChanged() 
	{
		checkAndEnable();
	}
}
