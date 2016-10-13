package EntityEditorGUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Editor extends JPanel
{
	public void Editor()
	{
		init();
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = 
			new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.insets.set(5, 5, 5, 5);
		
		addComponents(c);
	}
	
	public void init(){}
	
	public void addComponents(GridBagConstraints c){}
	
	public static void addLabel(
		JComponent comp,
		GridBagConstraints c, JLabel label,
		int gridx, int gridy)
	{
		c.gridx = gridx;
		c.gridy = gridy;
		
		c.weightx = 0;
		
		c.anchor = GridBagConstraints.LINE_END;
		
		comp.add(label, c);
	}
	
	public static void addEditableField(
			JComponent comp,
			GridBagConstraints c, JComponent field,
			int gridx, int gridy)
	{
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = 1;
		c.weightx = 1;
		
		c.anchor = GridBagConstraints.LINE_START;
		
		comp.add(field, c);
	}
		
	public static void addField(
			JComponent comp,
			GridBagConstraints c, JTextField field,
			int gridx, int gridy)
	{
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = 1;
		c.weightx = 1;
		
		c.anchor = GridBagConstraints.LINE_START;
		
		comp.add(field, c);
	}
	
	public static void addCheckBox(
			JComponent comp,
			GridBagConstraints c, JCheckBox box,
			int gridx, int gridy)
	{
		c.gridx = gridx;
		c.gridy = gridy;
		c.gridwidth = 1;
		
		c.weightx = 1;
		
		c.anchor = GridBagConstraints.LINE_START;
		
		comp.add(box, c);
	}
}
