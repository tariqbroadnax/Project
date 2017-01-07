package Editor.comp;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JPanel;

public class ColorField extends Form
{
	private Color color;
	
	public ColorField()
	{
		JPanel colorPnl = new JPanel() {
			public void paintComponent(Graphics g) {
				g.setColor(color);
				g.fillRect(0, 0, getWidth(), getHeight());
			}
		};
		
		JButton chooserBtn = new JButton();
		
		chooserBtn.addActionListener(e -> updateColor());
	
		addField(colorPnl, 0, 0, 1);
		addComponent(chooserBtn, 1, 0, 1);
	}
	
	private void updateColor()
	{
		color = JColorChooser.showDialog(null, "Color", color);
		repaint();
	}
}
