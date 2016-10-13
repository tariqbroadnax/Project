package EntityEditorGUI;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.JTextField;

import EditorGUI.SimpleDocumentListener;

public class DoubleTextField extends JTextField
	implements FieldContainer
{
	private double min, max;
	
	private double val;
	
	private Collection<FieldListener> listeners;

	public DoubleTextField(double start)
	{
		this(Double.NEGATIVE_INFINITY, Double.POSITIVE_INFINITY,
			 start);	
	}
	
	public DoubleTextField(
			double min, double max, double start)
	{
		super();
		
		this.min = min;
		this.max = max; 
		
		listeners = new LinkedList<FieldListener>();

		SimpleDocumentListener dl = e -> checkAndSetTextColor();
		
		FocusLostListener fl = e -> checkAndSetNewValue();
		ActionListener al = e -> checkAndSetNewValue();
		
		getDocument().addDocumentListener(dl);
		
		addFocusListener(fl);
		addActionListener(al);
		
		setText(min + "");
		val = min;
		
		setDoubleValue(start);
	}
	
	public void setDoubleValue(double val)
	{
		if(min <= val && val <= max)
		{
			setText(val + "");
			this.val = val;		
		}
	}
	
	public void addFieldListener(FieldListener list)
	{
		listeners.add(list);
	}
	
	public void removeFieldListener(FieldListener list)
	{
		listeners.remove(list);
	}
	
	public double getDoubleValue()
	{
		return val;
	}
	
	private void checkAndSetTextColor()
	{
		Double validVal = validValue();
		
		if(validVal == null)
			setForeground(Color.red);
		else 
			setForeground(Color.black);
	}
	
	private Double validValue()
	{
		double val = -1;
		
		try {
			val = Double.parseDouble(getText());
			
			if(min <= val && val <= max)
				return val;
			else
				return null;
		} catch (Exception ex) {
			return null;
		}	
	}
	
	private void checkAndSetNewValue()
	{
		Double validVal = validValue();
		
		if(validVal != null) {
			val = validVal;
			notifyListeners();
		}
		
		setText(this.val + "");
	}

	@Override
	public Collection<FieldListener> getFieldListeners() 
	{
		return listeners;
	}
}
