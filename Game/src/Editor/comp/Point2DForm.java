package Editor.comp;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import Editor.DoubleField;

public class Point2DForm extends Form
	implements ValueListener
{	
	private Point2D.Double pt;
	
	private DoubleField xFld, yFld;
		
	public Point2DForm()
	{
		this(0, 0);
	}
	
	public Point2DForm(double x, double y)
	{
		this(new Point2D.Double());
	}
	
	public Point2DForm(Point2D.Double pt1)
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
	}

	@Override
	public void valueChanged() {
		updateValues();
		notifyListeners();
	}
	
	public void setValue(double x, double y) {
		setValue(new Point2D.Double(x, y));
	}
	
	public void setValue(Point2D.Double pt) 
	{
		this.pt = pt;
		
		updateFields();
	}
	
	public void updateFields()
	{
		xFld.setValue(pt.x);
		yFld.setValue(pt.y);
	}
	
	private void updateValues()
	{
		double x = xFld.getValue(),
			   y = yFld.getValue();
		
		pt.x = x;
		pt.y = y;
	}
	
	public double getXValue() {
		return xFld.getValue();
	}
	
	public double getYValue() {
		return yFld.getValue();
	}
	
	public void setEnabled(boolean enabled) {
		super.setEnabled(enabled);
		
		xFld.setEnabled(enabled);
		yFld.setEnabled(enabled);
	}
	
	public Point2D.Double getPtValue() 
	{
		double x = xFld.getValue(),
			   y = yFld.getValue();
	
		return new Point2D.Double(x, y);
	}
}
