package Editor.edit;

import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DropTargetContext;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import Editor.EditorResources;
import Editor.selection.SelectionHandler;
import Entity.Entity;

public class SceneEditorTransferHandler extends TransferHandler
{
	public EditorResources resources;
	
	public SceneEditorTransferHandler(EditorResources resources)
	{
		this.resources = resources;
	}
	
	@Override
	public boolean canImport(TransferHandler.TransferSupport support)
	{
		for(DataFlavor flavor : support.getDataFlavors())
		{
			Class<?> c = flavor.getRepresentationClass();
			
	//		System.out.println(c);
			
			if(c.equals(Entity.class))
				return true;
		}
		
		return false;
	}
	
	@Override
	public boolean importData(TransferHandler.TransferSupport support)
	{
		DataFlavor flavor = new DataFlavor(
				Entity.class, "Entity");	
		
		Object o = null;
		try {
			o = support.getTransferable()
					   .getTransferData(flavor);
			
			EntitySelection selection = (EntitySelection) o;

			AddEntities edit = new AddEntities(selection.get(), resources);
	
			resources.getUndoManager()
					 .addEdit(edit);
			
			System.out.println("imported!!!");
			return true;
			
		} catch (UnsupportedFlavorException |
				 IOException e) {
			e.printStackTrace();
			
			return false;
		}
	}
	
	@Override
	public Transferable createTransferable(JComponent c)
	{
		SelectionHandler handler = resources.getSelectionHandler();
		
		List<Object> objs = handler.getSelection();
		
		List<Entity> ents = new ArrayList<Entity>(objs.size());
		
		for(Object obj : objs)
			ents.add((Entity) obj);
		
		EntitySelection selection = new EntitySelection(ents, false);
		
		return selection;
	}
	
	@Override
	public void exportDone(JComponent c, Transferable t, int action)
	{
		if(action == MOVE)
		{
			SelectionHandler handler = resources.getSelectionHandler();
			
			List<Object> objs = handler.getSelection();
		
			List<Entity> ents = new ArrayList<Entity>(objs.size());
			
			for(Object obj : objs)
				ents.add((Entity) obj);
			
			RemoveEntities edit = new RemoveEntities(ents, resources);
			
			edit.invoke();
			
			handler.removeSelection();
			
			resources.getUndoManager()
					 .addEdit(edit);	
		}
	}
	
	@Override
	public int getSourceActions(JComponent c) {
	     return COPY_OR_MOVE;
	}
}
