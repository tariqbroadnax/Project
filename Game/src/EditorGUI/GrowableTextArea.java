package EditorGUI;

import java.awt.FontMetrics;
import java.awt.Graphics;

import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

public class GrowableTextArea extends JTextArea
	implements DocumentListener
{	
	public GrowableTextArea()
	{
		setLineWrap(true);
		setWrapStyleWord(true);
		
		Document doc = getDocument();		
		doc.addDocumentListener(this);
	}
	
	private void updateSize()
	{
		Graphics g = getGraphics();
		
		if(g == null) return;
		
		int width = getSize().width;
		
		FontMetrics metrics = g.getFontMetrics();
		
		String text = getText();
		
		int textWidth = metrics.stringWidth(text);
		
		int rows = textWidth / width + 1;
		
		int lineHeight = metrics.getHeight();
				
		int height = lineHeight * rows;
		
		setSize(width, height);	
	}
	
	public void setWidth(int width)
	{
		int height = getHeight();
		setSize(width, height);
		updateSize();
	}

	@Override
	public void changedUpdate(DocumentEvent e) 
	{
		updateSize();
	}

	@Override
	public void insertUpdate(DocumentEvent e) 
	{
		updateSize();
	}

	@Override
	public void removeUpdate(DocumentEvent e) 
	{
		updateSize();
	}
}
