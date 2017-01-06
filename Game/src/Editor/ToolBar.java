package Editor;

import javax.swing.JToolBar;

import Editor.actions.Copy;
import Editor.actions.Cut;
import Editor.actions.New;
import Editor.actions.Open;
import Editor.actions.Paste;
import Editor.actions.Save;
import Editor.actions.SetEraseTool;
import Editor.actions.SetSelectTool;
import Editor.actions.SetStampTool;

public class ToolBar extends JToolBar
{
	public ToolBar(EditorResources resources)
	{
		add(new New(resources));
		add(new Open(resources));
		add(new Save(resources));
		
		addSeparator();

		add(new SetSelectTool(resources));
		add(new SetStampTool(resources));
		add(new SetEraseTool(resources));
		
		addSeparator();

		add(new Cut());
		add(new Copy());
		add(new Paste());
		
		addSeparator();
	}
}