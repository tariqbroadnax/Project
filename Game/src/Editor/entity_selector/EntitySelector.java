package Editor.entity_selector;

import java.awt.BorderLayout;
import java.util.Map;

import javax.swing.JPanel;

import Editor.EditorResources;
import Entity.Entity;

public class EntitySelector extends JPanel
{	
	private Map<Entity, EntityComponent> map;
	
	private EditorResources resources;
	
	public EntitySelector(EditorResources resources)
	{
		this.resources = resources;
		
		ToolBar toolbar = new ToolBar(resources);
		EntitySelectorPanel pnl = new EntitySelectorPanel(resources);
		
		setLayout(new BorderLayout());
		
		add(toolbar, BorderLayout.NORTH);
		add(pnl, BorderLayout.CENTER);	
	}

}
