package EntityEditorGUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import Graphic.Graphic;

public abstract class GraphicEditor extends JPanel
{
	private JLabel visibleLabel;
	
	private JRadioButton visibleButton;
	
	private Graphic graphic;
	
	public GraphicEditor()
	{
		this(null);
	}
	
	public GraphicEditor(Graphic graphic)
	{		
		init();
		
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = 
			new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.insets.set(5, 10, 5, 5);
	
		addComponents(c);		
	
		setGraphic(graphic);
		
		visibleButton.addActionListener(
				e -> setVisibility());
	}
	
	private void setVisibility()
	{
		boolean visible = 
				visibleButton.isSelected();
		
		graphic.setVisible(visible);
	}
	
	protected void init()
	{
		visibleLabel = new JLabel("Visible: ");
		
		visibleButton = new JRadioButton();
	}
	
	protected void addComponents(GridBagConstraints c)
	{
		Editor.addLabel(this, c, visibleLabel, 0, 0);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0;
		c.gridx = 1; c.gridy = 0;
		add(visibleButton, c);
	}
	
	public void setGraphic(Graphic graphic)
	{
		this.graphic = graphic;
		
		boolean visible = graphic.isVisible();
		
		visibleButton.setSelected(visible);		
	}
	
	public Graphic getGraphic()
	{
		return graphic;
	}	
}
