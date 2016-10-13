package EditorGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLayeredPane;
import javax.swing.JTextArea;
import javax.swing.border.Border;

public class TextButton extends JLayeredPane
{
	private JButton button;
	
	private GrowableTextArea textArea;
	
	private Dimension maxImgSize;
	
	public TextButton(BufferedImage img)
	{
		button = new JButton();
		textArea = new GrowableTextArea();
		
		maxImgSize = new Dimension(60, 60);
		
		textArea.setEditable(false);
		textArea.setBackground(new Color(0, 0, 0, 0));
		textArea.setOpaque(false);
		textArea.setBorder(null);
		textArea.setWidth(maxImgSize.width);
		
		Dimension prefSize = new Dimension(
				maxImgSize.width, 
				maxImgSize.height + 16);
		
		setPreferredSize(prefSize);
		
		add(button);
		setLayer(button, 0);
		
		add(textArea);
		setLayer(textArea, 1);
		
		ComponentSizeListener list = 
				e -> setButtonAndTextFieldSize(),
							  list2 =
				e -> updatePreferredSize();
	
		addComponentListener(list);
		
		ActionListener aList = 
				e -> toggleTextField();
	
		button.addActionListener(aList);
		
		textArea.addComponentListener(list2);
	}
	
	private void updatePreferredSize()
	{
		int width = maxImgSize.width;
		int textAreaHeight = textArea.getHeight(),
			height = maxImgSize.height + textAreaHeight;
		
		Dimension prefSize = new Dimension(
				width, height);
		
		setPreferredSize(prefSize);
	}
	
	private void toggleTextField()
	{
		if(textArea.isEditable())
		{
			textArea.setOpaque(false);
			textArea.setEditable(false);
			textArea.setHighlighter(null);
			textArea.setBorder(null);
			textArea.setBackground(new Color(0,0,0,0));
		}
		else
		{
			textArea.setOpaque(true);
			textArea.setBackground(Color.white);
			textArea.setEditable(true);
			Border border = 
					BorderFactory.createLineBorder(Color.black);
			textArea.setBorder(border);
		}
	}
	
	private void setButtonAndTextFieldSize()
	{
		Dimension size = getSize(),
				  buttonSize = size;
		
		Point buttonLoc = new Point(0,0),
			  textAreaLoc = new Point(
					  0, maxImgSize.height);
		
		button.setSize(buttonSize);
		button.setLocation(buttonLoc);
		textArea.setWidth(size.width);
		textArea.setLocation(textAreaLoc);
	}
	
	public JButton getBackgroundButton()
	{
		return button;
	}
	
	public JTextArea getFrontTextField()
	{
		return textArea;
	}
	
}
