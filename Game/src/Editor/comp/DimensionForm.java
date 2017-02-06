package Editor.comp;

import java.awt.Dimension;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JLabel;

import Editor.IntField;
import Editor.comp.Form;
import Editor.comp.ValueListener;

public class DimensionForm extends Form
	implements ValueListener
{
	private IntField wFld, hFld;
	
	public DimensionForm()
	{
		this(5, 5);
	}
	
	public DimensionForm(Dimension dim)
	{
		this(dim.width, dim.height);
	}
	
	public DimensionForm(int w, int h)
	{
		JLabel wLbl = new JLabel("Width"),
			   hLbl = new JLabel("Height");
		
		wFld = new IntField();
		hFld = new IntField();
		
		addComponent(wLbl, 0, 0, 1);
		addField(wFld, 1, 0, 1);
		addComponent(hLbl, 0, 1, 1);
		addField(hFld, 1, 1, 1);
		
		wFld.addValueListener(this);
		hFld.addValueListener(this);
		
		setDimension(w, h);
	}
	
	public void setDimension(int w, int h)
	{
		wFld.setValue(w);
		hFld.setValue(h);
	}
	
	public void setDimension(Dimension dim)
	{
		setDimension(dim.width, dim.height);
	}
	
	public Dimension getDimension()
	{
		int w = wFld.getValue(),
			h = hFld.getValue();
		
		Dimension dim = new Dimension(w, h);
		
		return dim;
	}

	@Override
	public void valueChanged() {
		notifyListeners();
	}
}
