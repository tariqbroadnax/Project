package Editor;

import Editor.tools.Tool;

public interface ResourceListener 
{
	public default void toolChanged(
			Tool prevTool, Tool newTool){}
	
	public default void tiledModeChanged(boolean tiledMode){}	
}
