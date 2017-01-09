package Editor.comp;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import Editor.DoubleField;

public class Point2DForm extends Form
	implements ValueListener
{	
	private DoubleField xFld, yFld;
	
	private List<ValueListener> listeners;
	
	public Point2DForm()
	{
		this(0, 0);
	}
	
	public Point2DForm(double x, double y)
	{
		JLabel xLbl = new JLabel("X"),
			   yLbl = new JLabel("Y");
		
		xFld = new DoubleField();
		yFld = new DoubleField();
		
		listeners = new ArrayList<ValueListener>();

		addComponent(xLbl, 0, 0, 1);
		addField(xFld, 1, 0, 1);
		addComponent(yLbl, 0, 1, 1);
		addField(yFld, 1, 1, 1);
		
		xFld.addValueListener(this);
		yFld.addValueListener(this);
	}
	
	private void notifyListeners()
	{
		for(ValueListener listener : listeners)
			listener.valueChanged();
	}
	
	public void addValueListener(ValueListener listener) {
		listeners.add(listener);
	}
	
	public void removeValueListener(ValueListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void valueChanged() {
		notifyListeners();
	}
	
	public void setXValue(double x) {
		xFld.setValue(x);
	}
	
	public void setYValue(double y) {
		yFld.setValue(y);
	}
	
	public void setValue(Point2D.Double pt) 
	{
		xFld.setValue(pt.x);
		yFld.setValue(pt.y);
	}
	
	public double getXValue() {
		return xFld.getValue();
	}
	
	public double getYValue() {
		return yFld.getValue();
	}
	
	public Point2D.Double getPtValue() 
	{
		double x = xFld.getValue(),
			   y = yFld.getValue();
	
		return new Point2D.Double(x, y);
	}
}
