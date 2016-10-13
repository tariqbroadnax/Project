package EditorGUI;

import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class Documents
{
	private Documents(){}

	public static String getText(Document doc)
	{
		int len = doc.getLength();
		
		try {
			return doc.getText(0, len);
		} catch (BadLocationException e) {
			return null; // This should never happen
		}
	}
}
