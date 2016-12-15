package Editor;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonModel;
import javax.swing.JButton;
import javax.swing.JTabbedPane;

public class TabButton extends JButton
	implements ActionListener
{
	private static final int WIDTH = 12,
							 HEIGHT = 12;
	
	private final JTabbedPane pane;
	private final ButtonTabComponent comp;
	
	public TabButton(final JTabbedPane pane,
			final ButtonTabComponent comp)
	{
		this.pane = pane;
		this.comp = comp;
		
		Dimension size = new Dimension(WIDTH, HEIGHT);
		
		setPreferredSize(size);
		setToolTipText("close tab");
		
		setContentAreaFilled(false);
		setFocusable(false);
		setBorderPainted(false);
		setRolloverEnabled(true);
		
		addActionListener(this);
	}
	
	@Override
	public void actionPerformed(ActionEvent e) 
	{
		int i = pane.indexOfTabComponent(comp);
		
		if(i != -1)
			pane.remove(i);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g = g.create();
	
		Graphics2D g2d = (Graphics2D) g;

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
							 RenderingHints.VALUE_ANTIALIAS_ON);
		
//		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
//				 		 	 RenderingHints.VALUE_ANTIALIAS_ON);

		
		ButtonModel model = getModel();
		
		if(model.isRollover())
		{
			g.setColor(Color.red);
			g.fillOval(1, 1, WIDTH - 1, HEIGHT - 1);
			g.setColor(Color.white);
		}
		else
			g.setColor(Color.gray);
	
		Stroke stroke = new BasicStroke(2);
		g2d.setStroke(stroke);
		
		g2d.drawLine(3, 3, WIDTH - 3, HEIGHT - 3);
		g2d.drawLine(3, HEIGHT - 3, WIDTH - 3, 3);
	
		g2d.dispose();
	}

}
