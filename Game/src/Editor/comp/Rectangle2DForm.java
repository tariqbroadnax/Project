package Editor.comp;

import java.awt.geom.Rectangle2D;

import javax.swing.JLabel;

import Editor.DoubleField;

public class Rectangle2DForm extends Form
	implements ValueListener
{
	private DoubleField xFld, yFld, wFld, hFld;
	
	private Rectangle2D.Double rect;
	
	public Rectangle2DForm()
	{
		this(new Rectangle2D.Double());
	}
	
	public Rectangle2DForm(Rectangle2D.Double rect)
	{
		JLabel xLbl = new JLabel("X"),
			   yLbl = new JLabel("Y"),
			   wLbl = new JLabel("Width"),
			   hLbl = new JLabel("Height");
		
		xFld = new DoubleField();
		yFld = new DoubleField();
		wFld = new DoubleField();
		hFld = new DoubleField();
		
		addComponent(xLbl, 0, 0, 1);
		addField(xFld, 1, 0, 1);
		addComponent(yLbl, 0, 1, 1);
		addField(yFld, 1, 1, 1);
		addComponent(wLbl, 0, 2, 1);
		addField(wFld, 1, 2, 1);
		addComponent(hLbl, 0, 3, 1);
		addField(hFld, 1, 3, 1);
		
		xFld.addValueListener(this);
		yFld.addValueListener(this);
		wFld.addValueListener(this);
		hFld.addValueListener(this);
		
		setRectangle(rect);
	}
	
	public void setRectangle(Rectangle2D.Double rect)
	{
		this.rect = rect;
		
		updateFields();
	}
	
	public Rectangle2D.Double getRectangle() {
		return (Rectangle2D.Double) rect.clone();
	}
	
	public void updateFields()
	{
		xFld.setValue(rect.x);
		yFld.setValue(rect.y);
		wFld.setValue(rect.width);
		hFld.setValue(rect.height);
	}
	
	private void updateValues()
	{
		double x = xFld.getValue(),
			   y = yFld.getValue(),
			   w = wFld.getValue(),
			   h = hFld.getValue();
		
		rect.x = x;
		rect.y = y;
		rect.width = w;
		rect.height = h;
	}
	
	@Override
	public void valueChanged()
	{
		updateValues();
		notifyListeners();
	}
}
