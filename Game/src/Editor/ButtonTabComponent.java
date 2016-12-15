package Editor;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

public class ButtonTabComponent extends JPanel
{
	private final JTabbedPane pane;
	
	private final TabButton button;
	
	public ButtonTabComponent(final JTabbedPane pane)
	{
		if(pane == null) 
			throw new NullPointerException("TabbedPane is null");
		
		this.pane = pane;
		
		setOpaque(false);
		
		TabLabel label = new TabLabel(pane, this);
		button = new TabButton(pane, this);
		
		add(label);
		add(button);
	}
	
	public TabButton getButton() {
		return button;
	}
}
