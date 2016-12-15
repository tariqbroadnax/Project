package EntityEditorGUI;

import java.awt.Color;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.LinkedList;

import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.text.Document;
import javax.swing.undo.UndoManager;

import EditorActions.Redo;
import EditorActions.Undo;
import EditorGUI.SimpleDocumentListener;

public abstract class NumberTextField extends JTextField
	implements ChangeNotifier
{
	private Collection<ChangeListener> listeners;

	protected UndoManager undoManager;

	public NumberTextField()
	{
		undoManager = new UndoManager();
	
		listeners = new LinkedList<ChangeListener>();	
	}
	
	@Override
	public Collection<ChangeListener> getChangeListeners() 
	{
		return listeners;
	}
	

	public static class Double extends NumberTextField
	{
		private double min, max;
		
		private double val;
		
		public Double(double start) 
		{
			this(java.lang.Double.NEGATIVE_INFINITY,
				 java.lang.Double.POSITIVE_INFINITY,
				 start);	
		}
		
		public Double(
				double min, double max, double start) 
		{
			super();
			
			if(min > max)
				throw new IllegalArgumentException(
						"min is greater than max");
			
			this.min = min;
			this.max = max;
			
			SimpleDocumentListener dl = e -> checkAndSetTextColor();
			
			FocusLostListener fl = e -> checkAndSetNewValue();
			ActionListener al = e -> checkAndSetNewValue();
			
			Document doc = getDocument();
			
			doc.addDocumentListener(dl);
			doc.addUndoableEditListener(undoManager);
			
			addFocusListener(fl);
			addActionListener(al);
			
			val = min;
			setText(min + "");
			
			setDoubleValue(val);
		}
		
		private void checkAndSetTextColor()
		{
			java.lang.Double validVal = validValue();
			
			if(validVal == null)
				setForeground(Color.red);
			else 
				setForeground(Color.black);
		}
	
		public void setDoubleValue(double val)
		{
			if(min <= val && val <= max)
			{
				setText(val + "");
				this.val = val;		
			}
			else
				throw new IllegalArgumentException(
						"value is out of range");
		}	
		
		public double getDoubleValue()
		{
			return val;
		}
	
		private void checkAndSetNewValue()
		{
			java.lang.Double validVal = validValue();
			
			if(validVal != null) {
				val = validVal;
				notifyListeners();
			}
			
			setText(this.val + "");
		}
	
		private java.lang.Double validValue() 
		{
			try {
				double val = 
						java.lang.Double.parseDouble(getText());
			
				if(min <= val && val <= max)
					return val;
				else
					return null;
				
			} catch(NumberFormatException e) {
				return null;
			}
		}
	}
	
	public static class Integer extends NumberTextField
	{
		private int min, max;
		
		private int val;
		
		public Integer(int start) 
		{
			this(java.lang.Integer.MIN_VALUE,
				 java.lang.Integer.MAX_VALUE,
				 start);	
		}
		
		public Integer(
				int min, int max, int start) 
		{
			super();
			
			if(min > max)
				throw new IllegalArgumentException(
						"min is greater than max");
			
			this.min = min;
			this.max = max; 
			
			SimpleDocumentListener dl =
					e -> checkAndSetTextColor();
			
			FocusLostListener fl =
					e -> checkAndSetNewValue();
			ActionListener al = 
					e -> checkAndSetNewValue();
			
			Document doc = getDocument();
			
			doc.addDocumentListener(dl);
			doc.addUndoableEditListener(undoManager);
			
			addFocusListener(fl);
			addActionListener(al);
			
			val = min;
			setText(min + "");
			
			setIntValue(val);
		}
		
		private void checkAndSetTextColor()
		{
			java.lang.Integer validVal = validValue();
			
			if(validVal == null)
				setForeground(Color.red);
			else 
				setForeground(Color.black);
		}
	
		public void setIntValue(int val)
		{
			if(min <= val && val <= max)
			{
				setText(val + "");
				this.val = val;		
			}
			else
				throw new IllegalArgumentException(
						"value is out of range");
		}	
		
		public int getIntValue()
		{
			return val;
		}
	
		private void checkAndSetNewValue()
		{
			java.lang.Integer validVal = validValue();
			
			if(validVal != null) {
				val = validVal;
				notifyListeners();
			}
			
			setText(this.val + "");
		}
	
		private java.lang.Integer validValue() 
		{
			try {
				String text = getText();
				int val = java.lang.Integer.parseInt(
						text);
			
				if(min <= val && val <= max)
					return val;
				else
					return null;
				
			} catch(NumberFormatException e) {
				return null;
			}
		}
	}
	
	public static class Float extends NumberTextField
	{
		private float min, max;
		
		private float val;
		
		public Float(float start) 
		{
			this(java.lang.Float.NEGATIVE_INFINITY,
				 java.lang.Float.POSITIVE_INFINITY,
				 start);	
		}
		
		public Float(
				float min, float max, float start) 
		{
			super();
			
			if(min > max)
				throw new IllegalArgumentException(
						"min is greater than max");
			
			this.min = min;
			this.max = max; 
			
			SimpleDocumentListener dl =
					e -> checkAndSetTextColor();
			
			FocusLostListener fl =
					e -> checkAndSetNewValue();
			ActionListener al = 
					e -> checkAndSetNewValue();
			
			Document doc = getDocument();
			
			doc.addDocumentListener(dl);
			doc.addUndoableEditListener(undoManager);
			
			addFocusListener(fl);
			addActionListener(al);
			
			val = min;
			setText(min + "");
			
			setFloatValue(val);
		}
		
		private void checkAndSetTextColor()
		{
			java.lang.Float validVal = validValue();
			
			if(validVal == null)
				setForeground(Color.red);
			else 
				setForeground(Color.black);
		}
	
		public void setFloatValue(float val)
		{
			if(min <= val && val <= max)
			{
				setText(val + "");
				this.val = val;		
			}
			else {
				System.out.printf("%f %f %f", min, max, val);
				throw new IllegalArgumentException(
						"value is out of range");
			}
		}	
		
		public float getFloatValue()
		{
			return val;
		}
	
		private void checkAndSetNewValue()
		{
			java.lang.Float validVal = validValue();
			
			if(validVal != null) {
				val = validVal;
				notifyListeners();
			}
			
			setText(this.val + "");
		}
	
		private java.lang.Float validValue() 
		{
			try {
				String text = getText();
				float val = java.lang.Float.parseFloat(
						text);
			
				if(min <= val && val <= max)
					return val;
				else
					return null;
				
			} catch(NumberFormatException e) {
				return null;
			}
		}
	}	
}
