package Editor.entity_selector;

import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JPanel;

import Editor.EditorResources;
import Entity.Entity;
import Entity.Player;

public class EntitySelector extends JPanel
{	
	private Map<Entity, EntityComponent> map;
	
	private EditorResources resources;
	
	public EntitySelector(EditorResources resources)
	{
		this.resources = resources;
		
		map = new HashMap<Entity, EntityComponent>();
		
		setPreferredSize(new Dimension(200, 200));
		
		add(new Player());
	}
	
	public void add(Entity ent)
	{
		EntityComponent comp = new EntityComponent(
				resources, ent, this);
		
		map.put(ent, comp);
		add(comp);
	}
	
	public void remove(Entity ent)
	{
		EntityComponent comp = map.get(ent);
		
		map.remove(ent);
		remove(comp);
	}
}
