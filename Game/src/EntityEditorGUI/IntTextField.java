package EntityEditorGUI;

import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;

public class IntTextField extends JTextField
	implements FocusListener
{
	private int min, max;
	
	private int val;
	
	public IntTextField(int min, int max, int start)
	{
		super();
	
		this.min = min;
		this.max = max;
		
		addFocusListener(this);
		
		setText(min + "");
		val = min;
		
		setIntValue(start);
	}
	
	public void setIntValue(int val)
	{
		if(min <= val && val <= max)
		{
			setText(val + "");
			this.val = val;
		}
	}
	
	public int getIntValue()
	{
		return val;
	}

	@Override
	public void focusGained(FocusEvent e) {}

	@Override
	public void focusLost(FocusEvent e) 
	{
		int val = -1;
		boolean validInput;
		
		try {
			val = Integer.parseInt(getText());
			
			validInput = min <= val && val <= max;
			
		} catch (Exception ex) {
			validInput = false;
		}
		
		if(validInput)
			this.val = val;
		
		setText(this.val + "");
	}
}
