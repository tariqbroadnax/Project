package GUI;

import java.awt.Component;

import javax.swing.JScrollPane;

public class GScrollPane extends JScrollPane
{
	public GScrollPane(
			InventoryTable table,
			int verticalScrollHint,
			int horizontalScrollHint) 
	{
		super(table,
			  verticalScrollHint,
			  horizontalScrollHint);
	}

	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		
		Component comp = getComponent(0);
		comp.setSize(width, height);
	}
}
