package GUI.Item;

import javax.swing.JPanel;
import javax.swing.JTabbedPane;

class ItemPanel extends JPanel
{
	private JTabbedPane pane;
	
	public ItemPanel()
	{
		pane = new JTabbedPane();
		
		pane.addTab("Use", null);
		pane.addTab("Equip", null);
		pane.addTab("Pet", null);
		pane.addTab("Etc", null);
	}
}
