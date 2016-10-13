package GUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.time.Duration;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import Game.Updatable;
import Utilities.FontUtilities;
import Utilities.TimeUtilities;

public class MyMenu extends JComponent
	implements MouseListener, Updatable
{
	private boolean animating = true;
	
	private long horizMoveRate = 500,
			 	 vertMoveRate = 500;
	
	private int maxWidth = 50;
	
	private double horizShift = 0;

	private Timer timer;
	
	private Collection<JButton> buttons;
	private Collection<Point> buttonLocs;
	
	private Font buttonFont;
	private Dimension buttonDim;
	
	private JButton hoveredButton,
					toggleButton;
	
	private boolean buttonsVisible = true;
	
	public MyMenu()
	{
		buttons = new LinkedList<JButton>();
		buttonLocs = new LinkedList<Point>();
		
		buttonFont = new Font("Consolas", Font.PLAIN, 12);
		
		timer = new Timer(33, new SwingUpdater(this));
		
		timer.start();
		
		toggleButton = new JButton();
		toggleButton.addActionListener(e -> toggleButtons());
		
		setLayout(null);
		
		setBackground(Color.blue);	
	}
	
	public void update(Duration delta)
	{
		long mDelta = delta.toMillis();
		
		if(shouldSlideLeft())
			slideLeft(mDelta);
		
		else if(shouldSlideRight())
			slideRight(mDelta);	
		
		updateButtonLocs(mDelta);
	}
	
	public void setAnimating(boolean animating)
	{
		this.animating = animating;
	
		if(animating)
			timer.start();
		else
			timer.stop();
	}
	
	private void updateButtonLocs(long delta)
	{
		Iterator<Point> buttonLocIter =
				buttonLocs.iterator();
		
		int x = (int) (
				horizShift * getWidth() );
		
		int vertShift = (int) (
				getWidth() *
				(1.0 / vertMoveRate * delta) );
		
		Point loc;
		for(JButton button : buttons)
		{
			loc = buttonLocIter.next();
			
			loc.x = x;
			
			x += button.getWidth();
			
			if(hoveredButton == button && 
			   loc.y != 0)
			{
				loc.y -= vertShift;
				loc.y = loc.y >= 0 ?
						loc.y : 0;				
			}
			else if(hoveredButton != button &&
					loc.y != getHeight() / 2)
			{
				loc.y += vertShift;
				loc.y = loc.y < getHeight() / 2 ?
						loc.y : getHeight() / 2;
			}
			
			button.setLocation(loc);
		}
	}
	
	private boolean shouldSlideRight()
	{
		return !buttonsVisible && horizShift != .9;
	}
	
	private boolean shouldSlideLeft()
	{
		return buttonsVisible && horizShift != 0;
	}
	
	private void slideRight(long delta)
	{
		double incr =
				(1.0 / horizMoveRate * delta);
		
		horizShift += incr;
		horizShift = horizShift < .9 ? 
				horizShift : .9;		
	}
	
	private void slideLeft(long delta)
	{
		double incr =
				-(1.0 / horizMoveRate * delta);
		
		horizShift += incr;
		horizShift = horizShift > 0 ? 
				horizShift : 0;		
	}
	
	private void toggleButtons()
	{ 
		buttonsVisible = !buttonsVisible;
		
		removeAndAddAll();
	}
	
	public void paintComponent(Graphics g)
	{
		if(!buttonsVisible) return;
		
		int width = getWidth(),
			height = getHeight();
		
		g.setColor(getBackground());
		g.fillRect(0, height/2, width - 1, height/2 - 1);			
	}
	
	public void addButton(String text)
	{
		JButton button = new JButton(text);
		
		button.setMargin(new Insets(0, 0, 0, 0));
		button.setVerticalAlignment(SwingConstants.TOP);
		button.addMouseListener(this);
		
		buttonLocs.add(new Point(0, getHeight() / 2));
		
		buttons.add(button);
		
		updateButtonFormat();
		removeAndAddAll();
	}
	
	public void setSize(int width, int height)
	{
		super.setSize(width, height);
		
		if(buttons.size() > 0)
		{
			updateButtonFormat();
			removeAndAddAll();
		}
	}
	
	private void removeAndAddAll()
	{
		removeAll();
		
		addToggleButton();
		addNormalButtons();
	}
	
	private void addNormalButtons()
	{

		int x = 0,
			y = getHeight()/2;
			
		for(JButton button : buttons)
		{
			formatButton(button);
			
			button.setLocation(x, y);
			
			x += button.getWidth();
			
			add(button);
		}
	}
	
	private void addToggleButton()
	{
		int x = getWidth() * 9 / 10,
			y = getHeight() / 2;
		
		toggleButton.setSize(
				getWidth() / 10,
				getHeight() / 2);
		
		toggleButton.setLocation(x, y);
		
		System.out.println("here");
		add(toggleButton);
	}
	
	private void formatButton(JButton button)
	{
		button.setFont(buttonFont);
		button.setSize(buttonDim);
	}
	
	private void updateButtonFormat()
	{
		int width = getWidth() / buttons.size() * 9 / 10;
		
		width = width < maxWidth ? width : maxWidth;
				
		buttonDim = new Dimension(
				width, getHeight());
		
		Dimension textDim = new Dimension(
				buttonDim.width,
				buttonDim.height / 2);
		
		buttonFont =
				findButtonFont(textDim);
	}
	
	private Font findButtonFont(Dimension textDim)
	{	
		String longTxt = 
				findLongestButtonText();
					
		Font buttonFont =
				FontUtilities.fontWithDimension(
				this.buttonFont, textDim,
				longTxt, this);
		
		return buttonFont == null ?
				this.buttonFont :
				buttonFont;
	}	
	
	private String findLongestButtonText()
	{
		String text = "";
		
		for(JButton button : buttons)
			text = text.length() > button.getText().length() ?
				   text : button.getText();
		
		return text;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		hoveredButton = (JButton) e.getSource();
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		Point loc = hoveredButton.getLocation();
		//hoveredButton.setLocation(loc.x, getHeight()/2);
		hoveredButton = null;
	}

	@Override
	public void mousePressed(MouseEvent e) {}

	@Override
	public void mouseReleased(MouseEvent e) {}

}
