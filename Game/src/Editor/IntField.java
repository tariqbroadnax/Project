package Editor;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;

import Editor.comp.ValueListener;
import EditorGUI.SimpleDocumentListener;

public class IntField extends JTextField
	implements SimpleDocumentListener, ActionListener,
			   FocusListener
{
	private int val;
	
	private int min, max;
	
	private List<ValueListener> listeners;
	
	public IntField()
	{
		this(Integer.MIN_VALUE,
			 Integer.MAX_VALUE, 0);
	}
	
	public IntField(int min, int max, int start)
	{
		this.val = start;
		
		this.min = min;
		this.max = max;
		
		listeners = new ArrayList<ValueListener>();
		
		Document doc = getDocument();
		doc.addDocumentListener(this);
		
		addFocusListener(this);
		addActionListener(this);
		
		setText(val + "");
	}
	
	public void setValue(int val)
	{
		this.val = val;
		
		setText(val + "");
	}
	
	public int getValue()
	{
		return val;
	}
	
	
	public boolean validText()
	{
		String txt = getText();
		
		try {
			Integer.parseInt(txt);
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
		
			String txt = getText();
			
			val = Integer.parseInt(txt);
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

	@Override
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent e) 
	{
		if(validText())
			setText(val + "");
		
		notifyListeners();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(validText())
			setText(val + "");
		
		notifyListeners();
	}
}
