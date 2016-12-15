package EditorGUI;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.Timer;

public abstract class SelectionButton extends JButton
{		
	private GUIResources resources;
 	
	private boolean selected;
	
	private Timer timer;
	
	private float selectionDashPhase;
	
	private SelectionListener selectListener;
	private ActionListener actionListener;
	
	public SelectionButton(GUIResources resources)
	{
		this.resources = resources;		
		
		selected = false;
		
		timer = new Timer(250, e -> repaint());
		
		selectionDashPhase = 0;
		
		setMargin(new Insets(0, 0, 0, 0));
				
		selectListener = src -> repaint();
		resources.addResourceListener(selectListener);
		
		actionListener = e -> selectContent();
		addActionListener(actionListener);
	}
	
	public void removeNotify()
	{
		super.removeNotify();
		
		timer.stop();
		
		//resources.removeResourceListener(selectListener);
	}
	
	protected abstract Object getContent();
	
	private void selectContent()
	{
		Object selected = resources.getSelectedObject(),
			   content = getContent();

		if(content == selected)
		{
			resources.setSelectedObj(null);
			timer.stop();
		}
		else
		{
			resources.setSelectedObj(content);
			timer.start();
		}
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Object selected = resources.getSelectedObject(),
			   content = getContent();
		
		if(content == selected)
			paintSelectionOutline(g);
	}
	
	public void paintBorder(Graphics g)
	{
		super.paintBorder(g);
		
		if(selected)
			paintSelectionOutline(g);
	}
	
	private void paintSelectionOutline(Graphics g)
	{
		Graphics2D g2d = (Graphics2D)g.create();

		Dimension size = getSize();

		Stroke stroke = new BasicStroke(
				5f, BasicStroke.CAP_BUTT,
				BasicStroke.JOIN_BEVEL, 1.0f,
				new float[]{5}, selectionDashPhase);
		
		selectionDashPhase = (selectionDashPhase + 2) % 10;
				
		g2d.setColor(getForeground());
		g2d.setStroke(stroke);
		
		g2d.drawRect(0, 0, size.width - 1, size.height - 1);
	}
}