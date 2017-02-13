package Editor;

import javax.swing.Action;

public interface ActionSupportNotifier 
{
	public void addActionSupportListener(
			ActionSupportListener list);
	
	public void removeActionSupportListener(
			ActionSupportListener list);
	
	public boolean actionSupported(Action action);
}
