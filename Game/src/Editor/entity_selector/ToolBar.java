package Editor.entity_selector;

import javax.swing.JToolBar;

import Editor.EditorResources;

public class ToolBar extends JToolBar
{
	public ToolBar(EditorResources resources)
	{
		add(new AddEntity(resources));
		add(new RemoveEntity(resources));
	}
}
