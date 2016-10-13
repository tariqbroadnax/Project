package EditorGUI;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import EntityEditorGUI.EntityEditor;

public class Inspector extends JPanel
{
	public Inspector(GUIResources resources)
	{
		setLayout(new BorderLayout());
				
		SelectionListener list = 
				src -> inspect(src.getSelectedObject());
	
		resources.addResourceListener(list);
	
		setPreferredSize(new Dimension(250, 600));
	}
	
	public void inspect(Object obj)
	{		
		if(obj instanceof EntityWrapper)
		{
			EntityWrapper entityWrapper = (EntityWrapper)obj;
			EntityEditor editor = new EntityEditor(entityWrapper);
		
			add(editor);
			revalidate();
		}
	}
}
