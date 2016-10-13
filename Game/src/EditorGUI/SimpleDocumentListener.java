package EditorGUI;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

public interface SimpleDocumentListener extends DocumentListener
{
	public static DocumentListener documentListener(
			SimpleDocumentListener listener)
	{
		return listener;
	}
	
	public void documentChanged(DocumentEvent e);
	
	@Override
	public default void changedUpdate(DocumentEvent e) 
	{
		documentChanged(e);
	}

	@Override
	public default void insertUpdate(DocumentEvent e) 
	{
		documentChanged(e);
	}

	@Override
	public default void removeUpdate(DocumentEvent e) 
	{
		documentChanged(e);
	}
}
