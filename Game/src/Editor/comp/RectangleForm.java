package Editor.comp;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

public class RectangleForm extends Form
	implements ValueListener
{
	private PointForm ptForm;
	private DimensionForm dimForm;
	
	public RectangleForm()
	{
		ptForm = new PointForm();
		dimForm = new DimensionForm();
				
		addField(ptForm, 0, 0, 1);
		addField(dimForm, 0, 1, 1);
		
		ptForm.addValueListener(this);
		dimForm.addValueListener(this);
	}
	
	public void setRectangle(int x, int y, int w, int h)
	{
		ptForm.setPoint(x, y);
		dimForm.setDimension(w, h);
	}
	
	public void setRectangle(Rectangle rect)
	{
		setRectangle(rect.x, rect.y,
					 rect.width, rect.height);
	}
	
	public Rectangle getRectangle()
	{
		Point pt = ptForm.getPoint();
		Dimension dim = dimForm.getDimension();
		
		Rectangle rect = new Rectangle(pt.x, pt.y,
									   dim.width, dim.height);
		return rect;
	}

	@Override
	public void valueChanged() {
		notifyListeners();
	}
}
