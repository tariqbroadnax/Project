package GUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.Stack;

import javax.swing.JPanel;

import Game.GameResources;

public class DebugPanel extends JPanel
{
	private DebugMenu menu;
	
	private Stack<DebugMenu> stack;
	
	public DebugPanel(GameResources resources)
	{		
		setOpaque(false);
	
		stack = new Stack<DebugMenu>();
			
		setLayout(new BorderLayout());
		
		push(new MainDebugMenu(resources));
		
		setSize(200, 800);
		setVisible(false);
		setIgnoreRepaint(true);
	}
	
	public void push(DebugMenu menu)
	{
		stack.push(menu);
		removeAll();
		add(menu, BorderLayout.WEST);
		menu.requestFocus();
		revalidate();
	}
	
	public void pop()
	{
		if(stack.size() == 1)
			return;
		else
		{
			stack.pop();
			removeAll();
			add(stack.peek(), BorderLayout.WEST);
			stack.peek().requestFocus();
			revalidate();
		}
	}
	
	public void setVisible(boolean visible)
	{
		super.setVisible(visible);

		if(visible)
			stack.peek().requestFocus();
	}
	
	public Component add(Component comp)
	{
		return super.add(comp);
	}
}
