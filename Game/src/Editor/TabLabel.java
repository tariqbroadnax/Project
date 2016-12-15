package Editor;

import javax.swing.JLabel;
import javax.swing.JTabbedPane;

public class TabLabel extends JLabel
{
	private final JTabbedPane pane;
	
	private final ButtonTabComponent comp;
	
	public TabLabel(
			final JTabbedPane pane,
			final ButtonTabComponent comp)
	{
		if(pane == null)
			throw new NullPointerException("tabbed pane is null");
		
		if(comp == null)
			throw new NullPointerException("button tab component is null");
		
		this.pane = pane;
		this.comp = comp;
	}
	
	public String getText()
	{
		if(pane == null)
			return null;
		
		int i = pane.indexOfTabComponent(comp);
		
		if(i != -1)
			return pane.getTitleAt(i);
		
		return null;
	}
}
