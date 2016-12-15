package Editor;

import java.awt.Color;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;

import EditorGUI.SimpleDocumentListener;

public class IntField extends JTextField
	implements SimpleDocumentListener
{
	private int min, max;
	
	public IntField()
	{
		this(Integer.MIN_VALUE,
			 Integer.MAX_VALUE, 0);
	}
	
	public IntField(int min, int max, int start)
	{
		this.min = min;
		this.max = max;
		
		Document doc = getDocument();
		doc.addDocumentListener(this);
		
		setText(start + "");
	}
	
	public void setValue(int val)
	{
		setText(val + "");
	}
	
	public int getValue()
	{
		String txt = getText();

		return Integer.parseInt(txt);
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
			setForeground(Color.black);
		else
			setForeground(Color.red);
	}
}
