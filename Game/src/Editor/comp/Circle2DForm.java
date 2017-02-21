package Editor.comp;

import javax.swing.JLabel;

import Editor.DoubleField;
import Maths.Circle2D;

public class Circle2DForm extends Form
	implements ValueListener
{
	private DoubleField xFld, yFld, radFld;
	
	private Circle2D.Double circ;
	
	public Circle2DForm()
	{
		this(new Circle2D.Double());
	}
	
	public Circle2DForm(Circle2D.Double circ)
	{
		JLabel xLbl = new JLabel("X"),
			   yLbl = new JLabel("Y"),
			   radLbl = new JLabel("Radius");
		
		xFld = new DoubleField();
		yFld = new DoubleField();
		radFld = new DoubleField();
		
		addComponent(xLbl, 0, 0, 1);
		addField(xFld, 1, 0, 1);
		addComponent(yLbl, 0, 1, 1);
		addField(yFld, 1, 1, 1);
		addComponent(radLbl, 0, 2, 1);
		addField(radFld, 1, 2, 1);
		
		xFld.addValueListener(this);
		yFld.addValueListener(this);
		radFld.addValueListener(this);
		
		setCircle(circ);
	}
	
	public void setCircle(Circle2D.Double circ)
	{
		this.circ = circ;
		
		updateFields();
	}
	
	public Circle2D.Double getCircle() {
		return (Circle2D.Double) circ.clone();
	}
	
	public void updateFields()
	{
		xFld.setValue(circ.x);
		yFld.setValue(circ.y);
		radFld.setValue(circ.radius);
	}
	
	private void updateValues()
	{
		double x = xFld.getValue(),
			   y = yFld.getValue(),
			   rad = radFld.getValue();
		
		circ.x = x; circ.y = y;
		circ.radius = rad;
	}

	@Override
	public void valueChanged() 
	{
		updateValues();
		notifyListeners();
	}

}
