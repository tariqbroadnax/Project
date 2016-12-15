package EditorGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;

import Entity.Entity;
import EntityEditorGUI.EntityEditor;

public class Inspector extends JPanel
{
	private GUIResources resources;
	
	private EntityEditor entityEditor;
	
	public Inspector(GUIResources resources)
	{
		entityEditor = new EntityEditor();
		
		setLayout(new BorderLayout());

		SelectionListener list = 
				src -> inspectSelectedObj();
	
		this.resources = resources;
		
		resources.addResourceListener(list);
		
		setPreferredSize(new Dimension(250, 600));
	}
	
	private void inspectSelectedObj()
	{
		List<Object> selectedObjs =
				resources.getSelectedObjects();
		
		Object obj = selectedObjs.get(0);
		
		inspect(obj);
	}
	
	public void inspect(Object obj)
	{		
		if(obj instanceof Entity)
		{
			removeAll();
			
			Entity entity = (Entity)obj;
			
			entityEditor.setEntity(entity);
		
			add(entityEditor);
		}
		
		revalidate();
		repaint();
	}
}
