package EntityEditorGUI;

import static java.awt.GridBagConstraints.HORIZONTAL;
import static java.awt.GridBagConstraints.LINE_END;
import static java.awt.GridBagConstraints.LINE_START;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class RectShapeEditor extends JPanel
{
	private JLabel shapeLabel,
				   widthLabel, heightLabel;
	
	private JComboBox<String> shapeBox;
	
	private DoubleTextField widthField, heightField;
	
	private RectangularShape shape;
	
	private Collection<FieldListener> listeners;
	
	private String currShape;
	
	public RectShapeEditor()
	{
		this(new Rectangle2D.Double());
	}
	
	public RectShapeEditor(RectangularShape shape)
	{
		shapeLabel = new JLabel("Shape: ");
		widthLabel = new JLabel("Width: ");
		heightLabel = new JLabel("Height: ");
		
		String[] shapes = {"Rectangle", "Ellipse"};
		
		shapeBox = new JComboBox<String>(shapes);
	
		currShape = shapeBox.getSelectedItem().toString();
		
		
		widthField = new DoubleTextField(
				0, 100, shape.getWidth());
		
		heightField = new DoubleTextField(
				0, 100, shape.getHeight());

		listeners = new LinkedList<FieldListener>();

		setLayout(new GridBagLayout());

		GridBagConstraints c = 
				new GridBagConstraints();
		
		c.fill = HORIZONTAL;
		
		c.insets.set(5, 5, 5, 5);
				
		addComponents(c);
		
		FieldListener l = () -> updateShape();
		ActionListener l2 = e -> selectShape();
		
		heightField.addFieldListener(l);
		widthField.addFieldListener(l);
		shapeBox.addActionListener(l2);
	}
	
	private void updateShape()
	{
		double width = widthField.getDoubleValue(),
			   height = heightField.getDoubleValue();
		
		if(width == shape.getWidth() &&
		   height == shape.getHeight())
			return;
		
		shape.setFrame(
				shape.getX(), shape.getY(),
				width, height);
		
		notifyListeners();
	}
	
	private void notifyListeners()
	{
		for(FieldListener listener : listeners)
			listener.fieldChanged();
	}

	public void addFieldListener(FieldListener listener)
	{
		listeners.add(listener);
	}
	
	public void removeFieldListener(FieldListener listener)
	{
		listeners.remove(listener);
	}
	
	private void addComponents(GridBagConstraints c)
	{
		c.weightx = 0;
		c.anchor = LINE_END;
		c.gridx = 0; c.gridy = 0;
		add(shapeLabel, c);
		
		c.weightx = 1;
		c.anchor = LINE_START;
		c.gridx = 1; c.gridy = 0;
		add(shapeBox, c);

		c.weightx = 0;
		c.anchor = LINE_END;
		c.gridx = 0; c.gridy = 1;
		add(widthLabel, c);
		
		c.weightx = 1;
		c.anchor = LINE_START;
		c.gridx = 1; c.gridy = 1;
		add(widthField, c);
		
		c.weightx = 0;
		c.anchor = LINE_END;
		c.gridx = 0; c.gridy = 2;
		add(heightLabel, c);
		
		c.weightx = 1;
		c.anchor = LINE_START;
		c.gridx = 1; c.gridy = 2;
		add(heightField, c);
	}

	public void setShape(RectangularShape shape) 
	{
		this.shape = shape;
		
		widthField.setDoubleValue(shape.getWidth());
		heightField.setDoubleValue(shape.getHeight());
	}
	
	public RectangularShape getShape()
	{
		return shape;
	}
	
	private void selectShape()
	{
		String prevShape = currShape;
		
		currShape =	shapeBox.getSelectedItem()
							.toString();
		
		
		if(prevShape.equals(shape))
			return;
		
		switch(currShape)
		{
		case "Rectangle":
			setShape(new Rectangle2D.Double(0,0,10,10));
			break;
		case "Ellipse":
			setShape(new Ellipse2D.Double(0,0,10,10));
			break;
		}
		
		notifyListeners();
	}
	
	
}
