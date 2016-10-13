package EntityEditorGUI;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Point2DEditor extends JPanel
	implements FieldContainer
{
	private JLabel xLabel, yLabel;

	private DoubleTextField xField, yField;
	
	private Point2D.Double pt;
	
	private Collection<FieldListener> listeners;
	
	public Point2DEditor()
	{
		this(new Point2D.Double());
	}
	
	public Point2DEditor(Point2D.Double pt)
	{
		xLabel = new JLabel("x: ");
		yLabel = new JLabel("y: ");
		
		xField = new DoubleTextField(pt.x);
		yField = new DoubleTextField(pt.y);
		
		addComponents();
		
		FieldListener fl = () -> updatePoint2D();
		
		listeners = new LinkedList<FieldListener>();
		
		xField.addFieldListener(fl);
		yField.addFieldListener(fl);
		
		setPoint2D(pt);
	}
	
	private void updatePoint2D()
	{
		pt.x = xField.getDoubleValue();
		pt.y = yField.getDoubleValue();
		
		System.out.println(pt);
	}

	private void addComponents()
	{
		setLayout(new GridBagLayout());
		
		GridBagConstraints c = 
			new GridBagConstraints();
		
		c.fill = GridBagConstraints.HORIZONTAL;
		
		c.insets.set(5, 5, 5, 5);
		
		addLabel(c, xLabel, 0, 0);
		
		addField(c, xField, 1, 0);
		
		addLabel(c, yLabel, 0, 1);
		
		addField(c, yField, 1, 1);
	}
	
	private void addLabel(
		GridBagConstraints c, JLabel label,
		int gridx, int gridy)
	{
		c.gridx = gridx;
		c.gridy = gridy;
		
		c.weightx = 0;
		
		c.anchor = GridBagConstraints.LINE_END;
		
		add(label, c);
	}
	
	private void addField(
			GridBagConstraints c, JTextField field,
			int gridx, int gridy)
	{
		c.gridx = gridx;
		c.gridy = gridy;
		
		c.weightx = 1;
		
		c.anchor = GridBagConstraints.LINE_START;
		
		add(field, c);
	}

	public void setPoint2D(Point2D.Double pt)
	{
		this.pt = pt;
		
		xField.setDoubleValue(pt.x);
		yField.setDoubleValue(pt.y);		
	}

	@Override
	public Collection<FieldListener> getFieldListeners() 
	{
		return listeners;
	}
}
