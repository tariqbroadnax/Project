package Editor.comp;

import javax.swing.JLabel;

import Editor.DoubleField;
import Maths.Dimension2D;

public class Dimension2DForm extends Form
	implements ValueListener
{
	private DoubleField widthFld, heightFld;
	
	public Dimension2DForm()
	{
		JLabel widthLbl = new JLabel("Width"),
			   heightLbl = new JLabel("Height");
		
		widthFld = new DoubleField();
		heightFld = new DoubleField();
		
		addComponent(widthLbl, 0, 0, 1);
		addField(widthFld, 1, 0, 1);
		addComponent(heightLbl, 0, 1, 1);
		addField(heightFld, 1, 1, 1);
		
		widthFld.addValueListener(this);
		heightFld.addValueListener(this);
	}
	
	public void setValue(double width, double height)
	{
		widthFld.setValue(width);
		heightFld.setValue(height);
	}
	
	public double getWidthValue() {
		return widthFld.getValue();
	}
	
	public double getHeightValue() {
		return heightFld.getValue();
	}
	
	public Dimension2D.Double getDimValue() 
	{
		double width = widthFld.getValue(),
			   height = heightFld.getValue();
		
		Dimension2D.Double dim = 
				new Dimension2D.Double(width, height);
		
		return dim;
	}
	
	@Override
	public void valueChanged() {
		notifyListeners();

	}
}
