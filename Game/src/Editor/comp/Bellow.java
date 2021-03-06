package Editor.comp;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

public class Bellow extends JPanel
{
	private JComponent comp;
	
	public Bellow(JComponent comp)
	{
		this.comp = comp;
		
		setLayout(new BorderLayout());
		
		JButton visibilityBtn = new JButton() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);
				paintTriangle(g);
			}
		};
		
		visibilityBtn.setPreferredSize(new Dimension(15, 15));
		visibilityBtn.setBorderPainted(false);
		visibilityBtn.setContentAreaFilled(false);
		
		visibilityBtn.addActionListener(e -> toggleContentVisibility());
		
		JPanel head = new JPanel();
		
		head.setLayout(new BorderLayout());
		head.add(visibilityBtn, BorderLayout.WEST);

		add(head, BorderLayout.NORTH);
		add(comp, BorderLayout.CENTER);
	}
	
	private void paintTriangle(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				 RenderingHints.VALUE_ANTIALIAS_ON);
		
		boolean visible = comp.isVisible();
		int[] xs, ys;
		
		if(visible)
		{
			xs = new int[]{0, 7, 14};
			ys = new int[]{0, 14, 0};
		}
		else
		{
			xs = new int[]{0, 0, 14};
			ys = new int[]{0, 14, 7};
		}
		
		g.setColor(Color.black);
		g.fillPolygon(xs, ys, 3);
	}

	private void toggleContentVisibility()
	{		
		boolean visible = comp.isVisible();
		
		comp.setVisible(!visible);
		
		revalidate();
		repaint();
	}
}
