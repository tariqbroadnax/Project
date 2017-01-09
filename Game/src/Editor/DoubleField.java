package Editor;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;

import Editor.comp.ValueListener;
import EditorGUI.SimpleDocumentListener;

public class DoubleField extends JTextField
	implements SimpleDocumentListener
{
	private double min, max;
	
	private List<ValueListener> listeners;
	
	public DoubleField()
	{
		this(Double.NEGATIVE_INFINITY,
			 Double.POSITIVE_INFINITY, 0);
	}
	
	public DoubleField(double start)
	{
		this(Double.NEGATIVE_INFINITY,
				 Double.POSITIVE_INFINITY, start);
	}
	
	public DoubleField(double min, double max, double start)
	{
		this.min = min;
		this.max = max;
		
		listeners = new ArrayList<ValueListener>();
		
		Document doc = getDocument();
		doc.addDocumentListener(this);
		
		setText(start + "");
	}
	
	public void setValue(double val)
	{
		setText(val + "");
	}
	
	public double getValue()
	{
		String txt = getText();

		return 	Double.parseDouble(txt);
	}
	
	public boolean validText()
	{
		String txt = getText();
		
		try {
			Double.parseDouble(txt);
			return true;
		} catch(Exception e) {
			return false;
		}
	}
	
	@Override
	public void documentChanged(DocumentEvent e) 
	{
		if(validText())
		{
			setForeground(Color.black);
			notifyListeners();
		}
		else
			setForeground(Color.red);
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
}
