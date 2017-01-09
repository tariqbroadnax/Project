package Editor;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.util.List;

import javax.swing.JPanel;

import Editor.comp.EntityForm;
import Editor.selection.SelectionListener;
import Entity.Entity;

public class Inspector extends JPanel
	implements SelectionListener
{
	private EditorResources resources;
	
	private EntityForm entForm;
	
	public Inspector(EditorResources resources)
	{
		this.resources = resources;

		entForm = new EntityForm();
		
		setLayout(new BorderLayout());
		
		resources.getSelectionHandler()
				 .addSelectionListener(this);
	}
	
	private void inspectSelectedObj()
	{
		List<Object> selectedObjs =
				resources.getSelectionHandler()
						 .getSelection();
		
		Object obj = selectedObjs.get(0);
		
		inspect(obj);
	}
	
	public void inspect(Object obj)
	{		
		removeAll();
		
		System.out.println("here");
		
		if(obj instanceof Entity)
		{	
			Entity entity = (Entity)obj;
			
			entForm.setEntity(entity);
		
			add(entForm);
		}
		
		revalidate();
		repaint();
	}

	@Override
	public void selectionChanged() 
	{
		System.out.println("here");
		inspectSelectedObj();
	}
}
