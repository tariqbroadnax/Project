package Editor.comp;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import Editor.IntField;

public class PointForm extends Form 
	implements ValueListener
{	
	private IntField xFld, yFld;
	
	public PointForm()
	{
		this(0, 0);
	}
	
	public PointForm(Point pt)
	{
		this(pt.x, pt.y);
	}
	
	public PointForm(int x, int y)
	{
		JLabel xLbl = new JLabel("X"),
			   yLbl = new JLabel("Y");
		
		xFld = new IntField();
		yFld = new IntField();
		
		addComponent(xLbl, 0, 0, 1);
		addField(xFld, 1, 0, 1);
		addComponent(yLbl, 0, 1, 1);
		addField(yFld, 1, 1, 1);
		
		xFld.addValueListener(this);
		yFld.addValueListener(this);
		
		setPoint(x, y);
	}
	
	public void setPoint(int x, int y)
	{
		xFld.setValue(x);
		yFld.setValue(y);
	}
	
	public void setPoint(Point pt)
	{
		setPoint(pt.x, pt.y);
	}
	
	public Point getPoint()
	{
		int x = xFld.getValue(),
			y = yFld.getValue();
		
		Point pt = new Point(x, y);
		
		return pt;
	}

	@Override
	public void valueChanged() {
		notifyListeners();
	}
	
}
