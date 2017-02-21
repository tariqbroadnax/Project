package Editor.comp;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.text.Document;

import EditorGUI.SimpleDocumentListener;

public class TextField extends JTextField
	implements SimpleDocumentListener,
			   FocusListener, ActionListener
{
	private List<ValueListener> listeners;
	
	public TextField()
	{
		listeners = new ArrayList<ValueListener>();
	
		addFocusListener(this);
		addActionListener(this);
		
		Document doc = getDocument();
		
		doc.addDocumentListener(this);
	}
	
	public void setText(String txt)
	{
		Document doc = getDocument();
		
		doc.removeDocumentListener(this);
		
		super.setText(txt);
		
		doc.addDocumentListener(this);
	}

	public void addValueListener(ValueListener list) {
		listeners.add(list);
	}
	
	public void removeValueListener(ValueListener list) {
		listeners.remove(list);
	}
	
	private void notifyListeners() 
	{
		for(ValueListener list : listeners)
			list.valueChanged();
	}

	@Override
	public void documentChanged(DocumentEvent e) {
		notifyListeners();
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		notifyListeners();
	}

	@Override
	public void focusGained(FocusEvent e) {	}

	@Override
	public void focusLost(FocusEvent e) {
		notifyListeners();
	}	
}
