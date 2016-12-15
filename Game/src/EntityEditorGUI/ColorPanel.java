package EntityEditorGUI;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ColorPanel extends JPanel
{
	private Color color;
	
	public ColorPanel(Color color)
	{
		this.color = color;
	}
	
	public ColorPanel()
	{
		color = Color.red;
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		g.setColor(color);
		
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}

	public Color getColor() {
		return color;
	}
}
