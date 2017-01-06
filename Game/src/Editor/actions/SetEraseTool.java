package Editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Editor.EditorResources;
import Editor.ResourceListener;
import Editor.tools.Tool;
import Utilities.GUIUtils;

public class SetEraseTool extends AbstractAction
implements ResourceListener
{
	private EditorResources resources;
	
	public SetEraseTool(EditorResources resources)
	{
		super("Erase");
		
		this.resources = resources;
		
		ImageIcon icon = GUIUtils.ImageIcon(
				"eraser.gif");
				
		resources.addResourceListener(this);
		
		Tool tool = resources.getTool();
		
		if(tool == resources.ERASE_TOOL)
			setEnabled(false);
		
		putValue(SMALL_ICON, icon);
		putValue(LARGE_ICON_KEY, icon);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		resources.setTool(resources.ERASE_TOOL);
		setEnabled(false);
	}
	
	public void toolChanged(Tool prevTool, Tool newTool)
	{
		setEnabled(newTool != resources.ERASE_TOOL);
	}
}
