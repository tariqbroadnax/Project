package Editor.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.ImageIcon;

import Editor.EditorResources;
import Editor.ResourceListener;
import Editor.tools.Tool;
import Utilities.GUIUtils;

public class SetStampTool  extends AbstractAction
implements ResourceListener
{
	private EditorResources resources;
	
	public SetStampTool(EditorResources resources)
	{
		super("Stamp");
		
		this.resources = resources;
		
		ImageIcon icon = GUIUtils.ImageIcon(
				"stamp.gif");
				
		resources.addResourceListener(this);
		
		Tool tool = resources.getTool();
		
		if(tool == resources.STAMP)
			setEnabled(false);
		
		putValue(SMALL_ICON, icon);
		putValue(LARGE_ICON_KEY, icon);	
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		resources.setTool(resources.STAMP);
		setEnabled(false);
	}
	
	public void toolChanged(Tool prevTool, Tool newTool)
	{
		setEnabled(newTool != resources.STAMP);
	}
}