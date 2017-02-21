package Editor.tools;

import java.util.ArrayList;
import java.util.List;

import Editor.Editor;
import Editor.EditorResources;

public class Tools 
{
	public final Stamp STAMP;
	public final SelectTool SELECTOR;
	public final EraseTool ERASER;
	public final MoveTool MOVER;
	
	private Tool tool;
	
	private List<ToolListener> listeners;
	
	public Tools()
	{
		EditorResources resources = Editor.RESOURCES;
		
		STAMP = new Stamp(resources);
		SELECTOR = new SelectTool(resources);
		ERASER = new EraseTool(resources);
		MOVER = new MoveTool(resources);
		
		listeners = new ArrayList<ToolListener>();
		
		tool = SELECTOR;
	}
	
	public void setTool(Tool tool) 
	{
		Tool prev = this.tool;
		
		this.tool = tool;
	
		for(ToolListener list : listeners)
			list.toolChanged(prev, tool);
	}
	
	public void addToolListener(ToolListener list) {
		listeners.add(list);
	}
	
	public void removeToolListener(ToolListener list) {
		listeners.remove(list);
	}
}
