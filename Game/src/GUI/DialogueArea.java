package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.font.FontRenderContext;
import java.awt.font.LineBreakMeasurer;
import java.awt.font.TextAttribute;
import java.text.AttributedCharacterIterator;
import java.text.AttributedString;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.Timer;
import javax.swing.event.DocumentEvent;

import Dialogue.Branch;
import Dialogue.Dialogue;
import EditorGUI.MouseListener;
import EditorGUI.SimpleDocumentListener;
import Event.Event;

public class DialogueArea extends JPanel
	implements SimpleDocumentListener, MouseListener
{
	private static final int CHARS_PER_SEC = 10;
	
	private Dialogue dialogue;
	
	private Timer timer;
	
	private int yOffset = 0;
	
	private int page = 0, visChars;	
	private long pageStart;
	
	private JTextArea area;
	private BranchList list;
	
	public DialogueArea()
	{
		area = new JTextArea();
		list = new BranchList(this);
		
		timer = new Timer(33, e -> updateText());
	
		setLayout(new BorderLayout());
		
		add(area, BorderLayout.CENTER);
		add(list, BorderLayout.SOUTH);
		
		Font font = new Font("Consolas", Font.PLAIN, 20);

		area.setFont(font);	
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setEditable(false);
		area.setHighlighter(null);
		
		area.getDocument().addDocumentListener(this);		
		area.addMouseListener(this);
		
		list.setVisible(false);
	}
	
	private void updateText()
	{
		long elapsed = System.currentTimeMillis() - pageStart;
		
		visChars = (int)(elapsed / 1000.0 * CHARS_PER_SEC);
	
		String txt = dialogue.getPage(page);
		
		if(txt.length() < visChars)
		{
			showFullPage();
		}
		else
		{
			String substr = txt.substring(0, visChars);
			
			area.setText(substr);
		}		
	}
	
	private void tryAndShowBranches()
	{
		int branchCount = dialogue.getBranchCount();
		if(branchCount > 0)
		{
			List<Branch> branches = dialogue.getBranches();
			
			list.setBranches(branches);
			list.setVisible(true);
		}
		else
			list.setVisible(false);
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
		if(this.dialogue == dialogue)
			return;
			
		setVisible(true);
		
		this.dialogue = dialogue;
		
		list.setVisible(false);
		
		page = 0;
		pageStart = System.currentTimeMillis();
		
		timer.start();
	}
	
	public void flipPage()
	{		
		if(page < dialogue.pageCount() - 1)
		{
			page++;
			pageStart = System.currentTimeMillis();
			
			timer.restart();
		}
		else
		{
			setVisible(false);
			
//			for(Event event : dialogue.getEvents())
//				event.invoke();
		}
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
		if(getGraphics() == null || area.getText().length() == 0) return;
		
		int lines = countLines(area),
			maxLines = maxLines();
		
		int txtHeight = getGraphics()
				 	   .getFontMetrics()
				 	   .getHeight();
		
		if(lines > maxLines)
			yOffset = -(lines - maxLines) * txtHeight;
		else 
			yOffset = 0;		
	}
	
	private boolean pageFullyShown()
	{
		return dialogue.getPage(page).length() == visChars;
	}
	
	private void showFullPage()
	{
		timer.stop();
		
		String txt = dialogue.getPage(page);
		
		area.setText(txt);
		visChars = txt.length();
		
		int pages = dialogue.pageCount();
		if(page == pages - 1)
		{			
			tryAndShowBranches();
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		if(!pageFullyShown())
			showFullPage();
		else
			flipPage();
	}
}
