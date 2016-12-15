package EntityEditorGUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RectangularShape;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

import Graphic.Graphic;
import Graphic.ShapeGraphic;

public class ShapeGraphicEditor extends GraphicEditor
	implements ChangeNotifier, ChangeListener,
			   ActionListener
{
	private JLabel filledLabel;
	
	private JRadioButton filledButton;
	
	private ColorEditor colorEditor;
	
	private RectShapeEditor shapeEditor;
	
	private StrokeEditor strokeEditor;
	
	private Border colorBorder,
				   shapeBorder,
				   strokeBorder;
	
	private Collection<ChangeListener> listeners;
	
	private boolean setting = false;
	
	public ShapeGraphicEditor(ShapeGraphic graphic) 
	{
		super(graphic);
		
		setGraphic(graphic);
	}
	
	public ShapeGraphicEditor()
	{
		this(new ShapeGraphic());
	}

	protected void init()
	{
		super.init();
		
		filledLabel = new JLabel("Filled: ");
		
		filledButton = new JRadioButton();
		
		colorEditor = new ColorEditor();
	    
	    shapeEditor = new RectShapeEditor();
	    
	    strokeEditor = new StrokeEditor();
	
	    listeners = new LinkedList<ChangeListener>();
	    
	    colorBorder = 
	    		BorderFactory.createTitledBorder(
	    		"Color");
	    
	    shapeBorder =
	    		BorderFactory.createTitledBorder(
	    		"Shape");
	    
	    strokeBorder = 
	    		BorderFactory.createTitledBorder(
				"Stroke");
	    
	    colorEditor.setBorder(colorBorder);
	    shapeEditor.setBorder(shapeBorder);
	    strokeEditor.setBorder(strokeBorder);
	    
	    filledButton.addActionListener(this);
	    colorEditor.addChangeListener(this);
	    strokeEditor.addChangeListener(this);
	    shapeEditor.addChangeListener(this);
	}
	
	protected void addComponents(GridBagConstraints c)
	{
		super.addComponents(c);
		
		c.anchor = GridBagConstraints.LINE_START;
		c.weightx = 0;
		c.gridx = 0; c.gridy = 1;
		add(filledLabel, c);
		
		c.anchor = GridBagConstraints.LINE_END;
		c.weightx = 0;
		c.gridx = 1; c.gridy = 1;
		add(filledButton, c);
		
		c.gridx = 0; c.gridy = 2;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1;
		c.gridwidth = 2;
		add(colorEditor, c);	
		
		c.gridx = 0; c.gridy = 3;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1;
		c.gridwidth = 2;
		add(shapeEditor, c);
		
		c.gridx = 0; c.gridy = 4;
		c.anchor = GridBagConstraints.CENTER;
		c.weightx = 1;
		c.gridwidth = 2;
		add(strokeEditor, c);
	}
	
	public void setShapeGraphic(ShapeGraphic graphic)
	{		
		setting = true;
		
		boolean filled = graphic.isFilled();
		Color color = (Color)graphic.getPaint();
		RectangularShape shape = graphic.getShape();
		BasicStroke stroke = graphic.getStroke();
	
		filledButton.setSelected(filled);
		colorEditor.setColor(color);
		shapeEditor.setShape(shape);
		strokeEditor.setStroke(stroke);
	
		setting = false;
	}

	public ShapeGraphic getShapeGraphic()
	{	
		boolean filled = filledButton.isSelected();
		Color color = colorEditor.getColor();
		BasicStroke stroke = strokeEditor.getStroke();
		RectangularShape shape = shapeEditor.getShape();
		
		ShapeGraphic graphic = new ShapeGraphic();
		
		graphic.setShape(shape); // this must go first
		graphic.setFilled(filled);
		graphic.setPaint(color);
		graphic.setStroke(stroke);
				
		return graphic;
	}

	@Override
	public Collection<ChangeListener> getChangeListeners() {
		return listeners;
	}

	@Override
	public void fieldChanged() {
		if(!setting)
			notifyListeners();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!setting)
			notifyListeners();
	}

	@Override
	protected void _setGraphic(Graphic graphic) {
		if(graphic instanceof ShapeGraphic)
			setShapeGraphic((ShapeGraphic)graphic);
		else
			throw new IllegalArgumentException();
	}

	@Override
	protected Graphic _getGraphic() {
		return getShapeGraphic();
	}
	
}
