package Editor.comp;

import javax.swing.BoxLayout;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Accordion extends JPanel
{
	public Accordion()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
	}
	
	public void add(JComponent comp)
	{
		Bellow bellow = new Bellow(comp);
		
		super.add(bellow);
	}
}