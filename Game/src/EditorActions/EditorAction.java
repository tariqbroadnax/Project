package EditorActions;

import javax.swing.AbstractAction;

import EditorGUI.GUIResources;

public abstract class EditorAction extends AbstractAction
{
	protected GUIResources resources;
	
	public EditorAction(GUIResources resources)
	{
		this.resources = resources;
	}
}
