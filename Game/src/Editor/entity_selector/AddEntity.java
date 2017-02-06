package Editor.entity_selector;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Editor.EditorAssets;
import Editor.EditorResources;
import Editor.selection.SelectionHandler;
import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Utilities.GUIUtils;

public class AddEntity extends AbstractAction
{
	private EditorResources resources;
	
	public AddEntity(EditorResources resources)
	{
		this.resources = resources;
		
		ImageIcon icon = GUIUtils.ImageIcon(
				"add.gif");
		
		putValue(SMALL_ICON, icon);
		putValue(LARGE_ICON_KEY, icon);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Entity ent = new Entity();
		
		EditorAssets assets = resources.getEditorAssets();
		
		ent.add(new GraphicsComponent());
		
		assets.addEntity(ent);	
		
		SelectionHandler handler = resources.getSelectionHandler();
		
		handler.setSelection(ent, false);
	
		resources.setTool(resources.STAMP);
	}	
}
