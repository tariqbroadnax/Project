package Editor;

import java.awt.BorderLayout;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Editor.comp.EntityForm;
import Editor.comp.ValueListener;
import Editor.selection.SelectionHandler;
import Editor.selection.SelectionListener;
import Entity.Entity;

public class Inspector extends JPanel
	implements SelectionListener, ValueListener
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
		
		entForm.addValueListener(this);
	}
	
	private void inspectSelectedObj()
	{
		List<Object> selectedObjs =
				resources.getSelectionHandler()
						 .getSelection();
		
		if(selectedObjs.size() > 0)
		{
			Object obj = selectedObjs.get(0);
			
			inspect(obj);
		}
	}
	
	public void inspect(Object obj)
	{		
		removeAll();
				
		if(obj instanceof Entity)
		{	
			Entity entity = (Entity)obj;
			
			entForm.setEntity(entity);
		
			add(new JScrollPane(entForm, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
										 JScrollPane.HORIZONTAL_SCROLLBAR_NEVER));
		}
		
		revalidate();
		repaint();
	}

	@Override
	public void selectionChanged() 
	{
		inspectSelectedObj();
	}

	@Override
	public void valueChanged() 
	{
		SelectionHandler handler = resources.getSelectionHandler();
		
		handler.notifySelectionModified();
	}
		
}
