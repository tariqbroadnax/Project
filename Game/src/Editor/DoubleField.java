package Editor;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.font.TextAttribute;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JPopupMenu;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.Document;
import javax.swing.undo.UndoableEdit;

import Editor.comp.ValueListener;
import EditorGUI.MouseListener;
import EditorGUI.SimpleDocumentListener;
import EditorGUI.UndoManager;
import Editor.actions.Actions;;


public class DoubleField extends JTextField
	implements SimpleDocumentListener, ActionListener,
			   FocusListener, UndoableEditListener,
			   MouseListener
{
	private double val;
	
	private double min, max;
	
	private List<ValueListener> listeners;
	
	private String guess = "0.000001";
	
	private UndoManager manager;
	
	private JPopupMenu popup = new JPopupMenu();
	
	
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
		this.val = start;
		
		this.min = min;
		this.max = max;
		
		listeners = new ArrayList<ValueListener>();
		
		manager = new UndoManager();
		
		Document doc = getDocument();
		
		doc.addDocumentListener(this);
		doc.addUndoableEditListener(this);
		
		addActionListener(this);
		addFocusListener(this);
		addMouseListener(this);
		
		setColumns(1); 
		// prevents resizing when text changed
		
		setText(val + "");	
		
		Font font = new Font("Consolas", Font.PLAIN, 12);
		
		setFont(font);
		
		popup.add(Actions.COPY);
		popup.add(Actions.CUT);
		popup.add(Actions.PASTE);
	}
	
	public void setValue(double val)
	{
		Document doc = getDocument();
		
		doc.removeDocumentListener(this);
		
		this.val = val;
		
		setText(val + "");
		
		doc.addDocumentListener(this);
	}
	
	public double getValue() {
		return val;
	}
	
	public boolean validText()
	{
		String txt = getText();
		
		try {
			double val = Double.parseDouble(txt);
			
			return min <= val && val <= max;
			
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
			setUnderlined(false);
			setValueWithText();
			
			notifyListeners();
		}
		else
		{
			setForeground(Color.red);
			setUnderlined(true);
		}
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
	public void focusGained(FocusEvent e) 
	{
		EditorResources.undo.setUndoManager(manager);
		EditorResources.redo.setUndoManager(manager);
	}

	@Override
	public void focusLost(FocusEvent e) 
	{
		if(!validText())
			setTextWithValue();
		
		notifyListeners();
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		if(!validText())
			setTextWithValue();
		
		notifyListeners();
	}
	
	private void setTextWithValue()
	{
		String txt = val + "";
		
		setText(txt);	
	}
	
	private void setValueWithText()
	{
		String txt = getText();

		val = Double.parseDouble(txt);
	}
	
	private void setUnderlined(boolean underlined)
	{
		Font font = getFont();
		
		Map attributes = font.getAttributes();
		
		if(underlined)
			attributes.put(TextAttribute.UNDERLINE,
						   TextAttribute.UNDERLINE_LOW_DOTTED);
		else
			attributes.put(TextAttribute.UNDERLINE,
						   null);
		
		font = font.deriveFont(attributes);
	
		setFont(font);
	}

	@Override
	public void undoableEditHappened(UndoableEditEvent e) 
	{
		UndoableEdit edit = e.getEdit();
		
		manager.addEdit(edit);		
	}
	
	@Override
	public void mouseReleased(MouseEvent e)
	{		
		if(e.isPopupTrigger())
		{
			int x = e.getX(), y = e.getY();
			
			popup.show(this, x, y);
		}
	}
}
