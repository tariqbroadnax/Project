package Editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import Editor.Editor;
import Editor.selection.SelectionHandler;
import Editor.selection.SelectionListener;
import Entity.Entity;

public class SetPlayer extends AbstractAction
	implements SelectionListener
{		
	private Entity ent;

	public SetPlayer()
	{
		super("Set as Player");
		
		setEnabled(false);
		
		Editor.RESOURCES.getSelectionHandler()
						.addSelectionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		Editor.RESOURCES.player = ent;
		
		setEnabled(false);
	}

	@Override
	public void selectionChanged() 
	{
		SelectionHandler handler = Editor.RESOURCES.getSelectionHandler();
		
		if(handler.instanceSelection(Entity.class))
		{
			ent = (Entity) handler.getSelection().get(0); 
			
			setEnabled(ent != null && Editor.RESOURCES.player != ent && handler.sceneSelection());
		}
	}
}
