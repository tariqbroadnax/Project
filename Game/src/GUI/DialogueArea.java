package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;

import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;

import Dialogue.Dialogue;
import EditorGUI.SimpleDocumentListener;

public class DialogueArea extends JTextArea
	implements SimpleDocumentListener
{
	private static final int CHARS_PER_SEC = 10;
	
	private Dialogue dialogue;
	
	private Timer timer;
	
	private long start;
	
	private int yOffset = 0;

	private int page = 0;
	
	public DialogueArea()
	{
		timer = new Timer(33, e -> updateText());
	
		setLineWrap(true);
		setWrapStyleWord(true);
		setEditable(false);
		
		Font font = new Font("Consolas", Font.PLAIN, 20);
		
		getDocument().addDocumentListener(this);
		
		setFont(font);	

		setPreferredSize(new Dimension(400, 100));
	}
	
	private void updateText()
	{
		long elapsed = System.currentTimeMillis() - start;
		
		int visChars = (int)(elapsed / 1000.0 * CHARS_PER_SEC);
	
		String txt = dialogue.getPage(page);
		
		if(txt.length() < visChars)
		{
			setText(txt);
		
			if(page + 1 < dialogue.pageCount())
			{
				page++;
				start = System.currentTimeMillis();
			}
			else
				timer.stop();
		}
		else
		{
			String substr = txt.substring(0, visChars);
			setCaretPosition(getDocument().getLength());
			setText(substr);
		}		
	}
	
	public void paintComponent(Graphics g)
	{
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
		
		Graphics2D g2d = (Graphics2D) g.create();
		
		g2d.translate(0, yOffset);
			
		super.paintComponent(g2d);
	}

	public void setDialogue(Dialogue dialogue)
	{
		this.dialogue = dialogue;
	
		page = 0;
		
		start = System.currentTimeMillis();
		
		timer.start();
	}
	
	private int maxLines()
	{
		FontMetrics metrics = getGraphics()
							 .getFontMetrics();
		
		int height = getHeight(),
			txtHeight = metrics.getHeight();
		
		return height / txtHeight;
	}

	private static int countLines(JTextArea textArea)
	{
	    AttributedString text = new AttributedString(textArea.getText());
	    text.addAttribute(TextAttribute.FONT, textArea.getFont());
	    FontRenderContext frc = textArea.getFontMetrics(textArea.getFont()).getFontRenderContext();
	    AttributedCharacterIterator charIt = text.getIterator();
	    LineBreakMeasurer lineMeasurer = new LineBreakMeasurer(charIt, frc);
	    Insets textAreaInsets = textArea.getInsets();
	    float formatWidth = textArea.getWidth() - textAreaInsets.left - textAreaInsets.right;
	    lineMeasurer.setPosition(charIt.getBeginIndex());

	    int noLines = 0;
	    while (lineMeasurer.getPosition() < charIt.getEndIndex())
	    {
	        lineMeasurer.nextLayout(formatWidth);
	        noLines++;
	    }

	    return noLines;
	}
	@Override
	public void documentChanged(DocumentEvent e) 
	{
		if(getGraphics() == null || getText().length() == 0) return;
		
		int lines = countLines(this),
			maxLines = maxLines();
		
		int txtHeight = getGraphics()
				 	   .getFontMetrics()
				 	   .getHeight();
		
		if(lines > maxLines)
			yOffset = -(lines - maxLines) * txtHeight;
		else 
			yOffset = 0;		
	}
}