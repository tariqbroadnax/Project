package EditorMenuBar;

import javax.swing.JMenuItem;

import EditorActions.Save;
import EditorGUI.EditorFrame;
import EditorGUI.GUIResources;
import EditorGUI.ResourceListener;
import Entity.Entity;

public class SaveItem extends JMenuItem
	implements ResourceListener
{
	public SaveItem(GUIResources resources, Save save)
	{
		super("Save - Ctrl+S");
				
		addActionListener(save);
		
		resources.addResourceListener(this);
		
		setEnabled(false);
	}

	@Override
	public void sceneLoaded(GUIResources src) {
		setEnabled(false);
	}

	@Override
	public void sceneChanged(GUIResources src) {
		setEnabled(true);
	}

	@Override
	public void sceneSaved(GUIResources src) {
		setEnabled(false);
	}

	@Override
	public void objectSelected(GUIResources src) {}

	@Override
	public void entityAdded(GUIResources src, Entity entity) {}

	@Override
	public void entityRemoved(GUIResources src, Entity entity) {}
}
