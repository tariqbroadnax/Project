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
	implements ChangeNotifier, ActionListener,
			   ChangeListener
{
	private JLabel shapeLabel,
				   widthLabel, heightLabel;
	
	private RectangularShapeChooser chooser;
	
	private NumberTextField.Double widthField, heightField;
		
	private Collection<ChangeListener> listeners;
		
	public RectShapeEditor()
	{
		this(new Rectangle2D.Double());
	}
	
	public RectShapeEditor(RectangularShape shape)
	{
		shapeLabel = new JLabel("Shape: ");
		widthLabel = new JLabel("Width: ");
		heightLabel = new JLabel("Height: ");
		
		chooser = new RectangularShapeChooser(shape);		
		
		double width = shape.getWidth(),
			   height = shape.getHeight();
		
		widthField = new NumberTextField.Double (
				0, 100, width);
		
		heightField = new NumberTextField.Double (
				0, 100, height);

		listeners = new LinkedList<ChangeListener>();

		setLayout(new GridBagLayout());

		GridBagConstraints c = 
				new GridBagConstraints();
		
		c.fill = HORIZONTAL;
		
		c.insets.set(5, 5, 5, 5);
				
		addComponents(c);
		
		heightField.addChangeListener(this);
		widthField.addChangeListener(this);
		chooser.addActionListener(this);
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
		add(chooser, c);

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
		widthField.setDoubleValue(shape.getWidth());
		heightField.setDoubleValue(shape.getHeight());
	
		chooser.setShape(shape);
	}
	
	public RectangularShape getShape()
	{
		RectangularShape shape = chooser.getShape();
		
		double width = widthField.getDoubleValue(),
			   height = heightField.getDoubleValue();
		
		shape.setFrame(0, 0, width, height);
		
		return shape;
	}

	@Override
	public void fieldChanged() {
		notifyListeners();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		notifyListeners();
	}

	@Override
	public Collection<ChangeListener> getChangeListeners() {
		return listeners;
	}
	
	
}
