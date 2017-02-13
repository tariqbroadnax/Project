package Editor.edit;

import java.awt.datatransfer.Clipboard;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.TransferHandler;

import Editor.EditorResources;
import Entity.Entity;

public class SceneEditorTransferHandler extends TransferHandler
{
	public EditorResources resources;
	
	public SceneEditorTransferHandler(EditorResources resources)
	{
		this.resources = resources;
	}
	
	public void exportToClipboard(
			JComponent comp, Clipboard clip, int action)
	{
		System.out.println("exporting...");
		List<Object> objs = resources.getSelectionHandler()
									 .getSelection();
		
		List<Entity> ents = new ArrayList<Entity>(objs.size());
		
		for(Object obj : objs)
			ents.add((Entity) obj);
		
		EntityList entList = new EntityList(ents);
		
		clip.setContents(entList, null);
	}
	
	public int getSourceActions(JComponent c) {
	        return COPY;
	}
}
