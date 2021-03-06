package Editor.comp;

import javax.swing.JLabel;

import Editor.DoubleField;
import Graphic.Vector2D;

public class Vector2DForm extends Form 
	implements ValueListener
{
	private DoubleField xFld, yFld;
	
	private Vector2D.Double v;
	
	public Vector2DForm()
	{
		this(new Vector2D.Double());
	}
	
	public Vector2DForm(Vector2D.Double v)
	{
		JLabel xLbl = new JLabel("X"),
			   yLbl = new JLabel("Y");
		
		xFld = new DoubleField();
		yFld = new DoubleField();
		
		addComponent(xLbl, 0, 0, 1);
		addField(xFld, 1, 0, 1);
		addComponent(yLbl, 0, 1, 1);
		addField(yFld, 1, 1, 1);
		
		xFld.addValueListener(this);
		yFld.addValueListener(this);
		
		setVector(v);
	}
	
	public void setVector(Vector2D.Double v)
	{
		this.v = v;
		
		updateFields();
	}
	
	public Vector2D.Double getVector() {
		return (Vector2D.Double) v.clone();
	}
	
	public void updateFields()
	{
		xFld.setValue(v.x);
		yFld.setValue(v.y);
	}
	
	private void updateValues()
	{
		double x = xFld.getValue(),
			   y = yFld.getValue();
		
		v.x = x; v.y = y;
	}

	@Override
	public void valueChanged() 
	{
		updateValues();
		notifyListeners();
	}
}
