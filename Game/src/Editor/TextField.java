package Editor;

import java.awt.event.ComponentEvent;

import javax.swing.JTextField;

public class TextField extends JTextField 
	implements ComponentListener
{
	public TextField(String txt)
	{
		super(txt);
		
		setColumns(1);
	}
	
	private void disableTextExpand()
	{
		int width = getWidth(),
			colW = getColumnWidth();
		
		setColumns(width/colW - 1);
	}
	
	@Override
	public void componentShown(ComponentEvent e) {
		disableTextExpand();
	}
	
	@Override
	public void componentResized(ComponentEvent e) {		
		disableTextExpand();
	}
}
