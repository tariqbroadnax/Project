package Editor.comp;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.RectangularShape;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JCheckBox;
import javax.swing.JLabel;

import Graphic.ShapeGraphic;

public class ShapeGraphicForm extends Form
	implements ValueListener, ActionListener
{
	private ShapeGraphic graph;
	
	private ShapeForm shapeForm;
	
	private ColorField colorField;
	
	private JCheckBox filledBox;
		
	public ShapeGraphicForm()
	{
		this(new ShapeGraphic());
	}
	
	public ShapeGraphicForm(ShapeGraphic graph)
	{
		JLabel colorLbl = new JLabel("Color"),
			   filledLbl = new JLabel("Filled");
		
		shapeForm = new ShapeForm();
		
		colorField = new ColorField();
		
		filledBox = new JCheckBox();

		addField(shapeForm, 0, 0, 2);
		addComponent(colorLbl, 0, 1, 1);
		addField(colorField, 1, 1, 1);
		addComponent(filledLbl, 0, 2, 1);
		addComponent(filledBox, 1, 2, 1);
		
		shapeForm.addValueListener(this);
		colorField.addValueListener(this);
		filledBox.addActionListener(this);
		
		shapeForm.getPtForm()
				 .setEnabled(false);
		
		setShapeGraphic(graph);
		
	}
	
	public void setShapeGraphic(ShapeGraphic graph)
	{
		this.graph = graph;
	
		updateFields();
	}
	
	public ShapeGraphic getShapeGraphic() {
		return graph;
	}
	
	public void updateFields()
	{
		RectangularShape shape = graph.getShape();
		
		Color color = (Color) graph.getPaint();
		
		boolean filled = graph.isFilled();
		
		shapeForm.setShape(shape);
		colorField.setValue(color);
		filledBox.setSelected(filled);
	}
	
	private void updateValues()
	{
		RectangularShape shape = shapeForm.getShape();

		Color color = colorField.getValue();
		
		boolean filled = filledBox.isSelected();
		
		graph.setShape(shape);
		graph.setPaint(color);
		graph.setFilled(filled);
	}

	@Override
	public void valueChanged() {
		updateValues();
		notifyListeners();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		updateValues();
		notifyListeners();
	}
}
