package EntityEditorGUI;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;
import java.awt.geom.RectangularShape;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.border.Border;

import Graphic.Graphic;
import Graphic.ShapeGraphic;

public class ShapeGraphicEditor extends GraphicEditor
{
	private JLabel filledLabel;
	
	private JRadioButton filledButton;
	
	private ColorEditor colorEditor;
	
	private RectShapeEditor shapeEditor;
	
	private StrokeEditor strokeEditor;
	
	private Border colorBorder,
				   shapeBorder,
				   strokeBorder;
	
	private ShapeGraphic graphic;
	
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
	   
	    ActionListener l = e -> updateGraphic();
	    FieldListener l2 = () -> updateGraphic();
	    
	    filledButton.addActionListener(l);
	    colorEditor.addActionListener(l);
	    strokeEditor.addActionListener(l);
	    shapeEditor.addFieldListener(l2);
	}
	
	private void updateGraphic()
	{
		boolean filled = filledButton.isSelected();
		Color color = colorEditor.getColor();
		BasicStroke stroke = strokeEditor.getStroke();
		RectangularShape shape = shapeEditor.getShape();
		
		// shape editor handles shape automatically

		graphic.setFilled(filled);
		graphic.setPaint(color);
		graphic.setStroke(stroke);
		graphic.setShape(shape);
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
	
	public void setGraphic(ShapeGraphic graphic)
	{
		super.setGraphic(graphic);
		
		this.graphic = graphic;
		
		boolean filled = graphic.isFilled();
		Color color = (Color)graphic.getPaint();
		RectangularShape shape = graphic.getShape();
		BasicStroke stroke = graphic.getStroke();
	
		filledButton.setSelected(filled);
		colorEditor.setColor(color);
		shapeEditor.setShape(shape);
		strokeEditor.setStroke(stroke);
	}

	public void setGraphic(Graphic graphic)
	{
		setGraphic((ShapeGraphic)graphic);
	}
	
}
