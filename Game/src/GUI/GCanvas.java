package GUI;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import Graphic.Painter;

public class GCanvas extends JPanel
{	
	private Painter painter;
	
	public GCanvas()
	{
		//addFocusListener(new KeepFocus());
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		
		painter.paint(g, getSize());
	}
	
	public void setPainter(Painter painter)
	{
		this.painter = painter;
	}
}
