package Editor.comp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import javax.swing.JComboBox;

public class ShapeForm extends Form
	implements ValueListener, ActionListener
{
	private RectangularShape shape;
	
	private JComboBox<String> shapeBox;

	private Point2DForm ptForm;
	private Dimension2DForm dimForm;
	
	public ShapeForm()
	{
		String[] shapeStrs = {"Rectangle", "Ellipse"};
		
		shapeBox = new JComboBox<String>(shapeStrs);
		
		ptForm = new Point2DForm();
		dimForm = new Dimension2DForm();

		addComponent(shapeBox, 0, 0, 1);
		addField(ptForm, 0, 1, 1);
		addField(dimForm, 0, 2, 1);
		
		shapeBox.addActionListener(this);
		ptForm.addValueListener(this);
		dimForm.addValueListener(this);		
	}
	
	public void setShape(RectangularShape shape)
	{
		this.shape = (RectangularShape) shape.clone();
	
		updateFields();
	}
	
	public void updateFields()
	{
		shapeBox.removeActionListener(this);
		
		double x = shape.getX(),
			   y = shape.getY(),
			   width = shape.getWidth(),
			   height = shape.getHeight();
		
		if(shape instanceof Rectangle2D.Double)
			shapeBox.setSelectedItem("Rectangle");
		else if(shape instanceof Ellipse2D.Double)
			shapeBox.setSelectedItem("Ellipse");
		
		ptForm.setValue(x, y);
		dimForm.setValue(width, height);
	
		shapeBox.addActionListener(this);
	}
	
	private void updateValues()
	{
		double x = ptForm.getXValue(),
			   y = ptForm.getYValue(),
			   width = dimForm.getWidthValue(),
			   height = dimForm.getHeightValue();
		
		String shapeType = (String) shapeBox.getSelectedItem();
	
		if(shapeType.equals("Rectangle"))
			shape = new Rectangle2D.Double();
		else if(shapeType.equals("Ellipse"))
			shape = new Ellipse2D.Double();
		
		shape.setFrame(x, y, width, height);
	}
	
	public RectangularShape getShape() {
		return shape;
	}

	@Override
	public void valueChanged() {
		updateValues();
		notifyListeners();
	}
	
	@Override
	public void actionPerformed(ActionEvent e) {
		updateValues();
		notifyListeners();
	}
	
	public Point2DForm getPtForm() {
		return ptForm;
	}
}
