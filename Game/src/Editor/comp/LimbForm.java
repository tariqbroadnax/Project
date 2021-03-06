package Editor.comp;

import java.awt.geom.Rectangle2D;
import java.awt.geom.RectangularShape;

import javax.swing.BorderFactory;
import javax.swing.border.Border;

import EntityComponent.Limb;
import Graphic.Vector2D;
import Maths.Circle2D;

public class LimbForm extends Form implements ValueListener
{
	private Limb limb;

	private Vector2DForm offsetForm;
	
	private Rectangle2DForm rectForm;
	private Circle2DForm circForm;
	
	public LimbForm()
	{
		this(new Limb());
	}
	
	public LimbForm(Limb limb)
	{
		Border offsetBorder = BorderFactory.createTitledBorder("Offset"),
			   rectBorder = BorderFactory.createTitledBorder("Rectangle"),
			   circBorder = BorderFactory.createTitledBorder("Circle");
		
		offsetForm = new Vector2DForm();
		
		rectForm = new Rectangle2DForm();
		circForm = new Circle2DForm();
		
		offsetForm.setBorder(offsetBorder);
		rectForm.setBorder(rectBorder);
		circForm.setBorder(circBorder);
		
		offsetForm.addValueListener(this);
		rectForm.addValueListener(this);
		circForm.addValueListener(this);
		
		addField(offsetForm, 0, 0, 1);
		
		setLimb(limb);
	}
	
	public void setLimb(Limb limb)
	{
		this.limb = limb;
		
		updateFields();
	}
	
	public Limb getLimb() {
		return (Limb) limb.clone();
	}
	
	public void updateFields()
	{
		Vector2D.Double offset = limb.getOffset();
		
		RectangularShape shape = limb.getShape();
		
		offsetForm.setVector(offset);
		
		remove(rectForm); remove(circForm);
		if(shape instanceof Rectangle2D.Double)
		{
			addField(rectForm, 0, 1, 1);
			rectForm.setRectangle((Rectangle2D.Double) shape);
		}
		else 
		{
			addField(circForm, 0, 1, 1);
			circForm.setCircle((Circle2D.Double) shape);
		}
		
		revalidate();
		repaint();
	}
	
	private void updateValues()
	{
		Vector2D.Double offset = offsetForm.getVector();
	
		RectangularShape shape = limb.getShape();

		if(shape instanceof Rectangle2D.Double)
			shape = rectForm.getRectangle();
		else
			shape = circForm.getCircle();
		
		limb.setOffset(offset);
		limb.setShape(shape);
	}
	
	@Override
	public void valueChanged()
	{
		updateValues();
		notifyListeners();
	}
}
