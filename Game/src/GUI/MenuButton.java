package GUI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MenuButton extends JPanel 
implements FocusListener, MouseListener,
		   KeyListener
{
	private JLabel leftLabel, rightLabel;
	
	public MenuButton(String str, String str2)
	{
		leftLabel = new JLabel(str);
		rightLabel = new JLabel(str2);
		
		rightLabel.setHorizontalTextPosition(SwingConstants.TRAILING);
		
		setLayout(new BorderLayout());
		
		add(leftLabel, BorderLayout.WEST);
		add(rightLabel, BorderLayout.EAST);
		
		setBackground(Color.white);
		setOpaque(false);
		
		addFocusListener(this);
		addMouseListener(this);
		addKeyListener(this);
	}
	
	public void setRightText(String str)
	{
		rightLabel.setText(str);
	}
	

	@Override
	public void focusGained(FocusEvent e) {
		setOpaque(true);
	}

	@Override
	public void focusLost(FocusEvent arg0) {
		setOpaque(false);
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		requestFocus();
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		transferFocus();
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
		{
			((DebugPanel)getParent().getParent())
			.pop();
			return;
		}
		
		DebugMenu parent = (DebugMenu) getParent();
		
		int size = parent.getComponentCount(),
			index = parent.indexOf(this);
		
		if(e.getKeyCode() == KeyEvent.VK_UP)
			index--;
		else if(e.getKeyCode() == KeyEvent.VK_DOWN)
			index++;
		
		if(size == index)
			index = 0;
		else if(index < 0)
			index = size - 1;
		
		parent.getComponent(index)
			  .requestFocus();
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		
	}
}
