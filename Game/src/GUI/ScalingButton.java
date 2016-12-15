package GUI;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JButton;

import Utilities.Fonts;

public class ScalingButton extends JButton
	implements ComponentListener
{
	private double textHeightWeight;
	
	public ScalingButton(String text)
	{
		super(text);
		
		textHeightWeight = 0.8;
		
		addComponentListener(this);
	}

	private void setNewFont()
	{
		Font currFont = getFont();
		int fontHeight = (int)(getWidth() * textHeightWeight);
		Graphics g = getParent().getGraphics();
		
		
		Font newFont = Fonts.fontWithHeight(
					 currFont, fontHeight, g);
		
		setFont(newFont);		
	}
	
	@Override
	public void componentResized(ComponentEvent e) 
	{
		setNewFont();
	}

	public void componentShown(ComponentEvent e) {}
	public void componentHidden(ComponentEvent e) {}
	public void componentMoved(ComponentEvent e) {}
}
