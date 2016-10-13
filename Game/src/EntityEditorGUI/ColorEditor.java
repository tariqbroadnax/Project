package EntityEditorGUI;

import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class ColorEditor extends JPanel
{
	private Color color;
	
	private ColorPanel panel;
	
	private JButton chooserButton;

	private Collection<ActionListener> listeners;
	
	public ColorEditor()
	{
		this(Color.red);
	}
	
	public ColorEditor(Color color)
	{
		panel = new ColorPanel(color);
		
		chooserButton = new JButton();
				
		listeners = new LinkedList<ActionListener>();
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = 
				new GridBagConstraints();
			
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.insets.set(5, 10, 5, 5);
		
		addComponents(c);
	
		chooserButton.addActionListener(
				e -> selectColor());
		
		setColor(color);
	}
	
	private void addComponents(GridBagConstraints c)
	{
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 1;
		c.gridx = 0; c.gridy = 0;
		add(panel, c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0;
		c.gridx = 1; c.gridy = 0;
		add(chooserButton);
	}
	
	private void selectColor()
	{
		Color newColor = 
				JColorChooser.showDialog(
						null,"Choose Color", color);
		
		if(newColor != null)
		{
			color = newColor;
			
			panel.setColor(newColor);
			panel.repaint();
			
			int id = ActionEvent.ACTION_PERFORMED;
			String command = "COLOR CHANGED";
			
			ActionEvent e = new ActionEvent(
					this, id, command);
			
			for(ActionListener listener : listeners)
				listener.actionPerformed(e);
		}
	}
	
	public void addActionListener(
			ActionListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeActionListener(
			ActionListener listener)
	{
		listeners.remove(listener);
	}

	public void setColor(Color color)
	{
		this.color = color;
		panel.setColor(color);
	}
	
	public Color getColor() 
	{
		return color;
	}
}
