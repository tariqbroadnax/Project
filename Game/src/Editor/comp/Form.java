package Editor.comp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.AbstractButton;
import javax.swing.JLabel;
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
		gbc.gridwidth = gbc.gridheight = 1;
	}
	
	public void addLabel(JLabel label, int gridx, int gridy)
	{
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.LINE_START;
		
		add(label, gbc);
	}
	
	public void addField(JTextField field, int gridx, int gridy)
	{
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.HORIZONTAL;
		
		add(field, gbc);
	}
	
	public void addButton(AbstractButton button, int gridx, int gridy)
	{
		gbc.gridx = gridx;
		gbc.gridy = gridy;
		gbc.weightx = 0;
		gbc.fill = GridBagConstraints.NONE;
		gbc.anchor = GridBagConstraints.LINE_START;
		
		add(button, gbc);
	}	
}
