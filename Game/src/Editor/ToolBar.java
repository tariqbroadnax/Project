package Editor;

import java.awt.Component;

import javax.swing.JToolBar;

import Editor.actions.Copy;
import Editor.actions.Cut;
import Editor.actions.New;
import Editor.actions.Open;
import Editor.actions.Paste;
import Editor.actions.Save;
import Editor.actions.SetEraseTool;
import Editor.actions.SetMoveTool;
import Editor.actions.SetPlayer;
import Editor.actions.SetSelectTool;
import Editor.actions.SetStampTool;
import Editor.actions.ToggleTiledModeButton;

public class ToolBar extends JToolBar
{
	public ToolBar(EditorResources resources)
	{
		add(new New(resources));
		add(new Open(resources));
		add(new Save(resources));
		
		addSeparator();
		
		add(new Copy());
		add(new Cut());
		add(new Paste());
		
		addSeparator();
	
		add(resources.getUndoAction());
		add(resources.getRedoAction());
	
		addSeparator();
		
		add(new SetSelectTool(resources));
		add(new SetStampTool(resources));
		add(new SetEraseTool(resources));
		add(new SetMoveTool(resources));
		
		addSeparator();
		
		add(new ToggleTiledModeButton(resources));
		
		addSeparator();
		
		add(new PlayScene(resources));
		
		add(new SetPlayer());
	
		setFocusable(false);
		
		for(Component child : getComponents())
			child.setFocusable(false);
	}
}
