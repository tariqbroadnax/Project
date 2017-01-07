package Editor.comp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Form extends JPanel
{
	private GridBagConstraints gbc;
	
	public Form()
	{
		setLayout(new GridBagLayout());
	
		gbc = new GridBagConstraints();
		
		gbc.insets.set(5, 5, 5, 5);
		gbc.gridheight = 1;
	}
	
	public void addComponent(JComponent comp, int gridx, int gridy, int gridwidth)
	{
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.weightx = 0;
		gbc.gridwidth = gridwidth;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.LINE_START;
		
		add(comp, gbc);
	}
	
	public void addField(JComponent comp, int gridx, int gridy, int gridwidth)
	{
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.weightx = 1;
		gbc.gridwidth = gridwidth;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		add(comp, gbc);
	}
}
