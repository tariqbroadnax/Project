package Editor.comp;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Form extends JPanel
{
	protected GridBagConstraints gbc;
	
	private List<ValueListener> listeners;

	public Form()
	{
		setLayout(new GridBagLayout());
	
		gbc = new GridBagConstraints();
		
		listeners = new ArrayList<ValueListener>();

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
	
	public void updateFields() {}
	
	protected void notifyListeners()
	{
		for(ValueListener listener : listeners)
			listener.valueChanged();
	}
	
	public void addValueListener(ValueListener listener) {
		listeners.add(listener);
	}
	
	public void removeValueListener(ValueListener listener) {
		listeners.remove(listener);
	}

}
