package GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.Paint;
import java.util.function.Supplier;

import javax.swing.JPanel;

public class StatusBar extends JPanel
{
	private Paint barPaint;

	private Insets insets;
	
	private Supplier<Double> currVal,
							 maxVal;
		
	public StatusBar()
	{
		this(() -> 100.0, () -> 100.0);
	}
	
	public StatusBar(Supplier<Double> currVal, Supplier<Double> maxVal)
	{
		this.currVal = currVal;
		this.maxVal = maxVal;
		
		barPaint = Color.red;
		setBackground(Color.white);
		
		insets = new Insets(5, 5, 5, 5);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Graphics2D g2d = (Graphics2D)g;
 				
		double ratio = currVal.get() / maxVal.get();
		
		int width = getWidth() - insets.left - insets.right,
			height = getHeight() - insets.top - insets.bottom;
		
		g2d.setPaint(barPaint);
		g2d.fillRect(insets.left, insets.top,
					 (int)(ratio * width), height);
	}
	
	public void setBarPaint(Paint barPaint)
	{
		this.barPaint = barPaint;
	}
	
	public void setInsets(Insets insets)
	{
		this.insets = insets;
	}
	
	public void setCurrentValueSupplier(Supplier<Double> currVal)
	{
		this.currVal = currVal;
	}
	
	public void setMaxValueSupplier(Supplier<Double> maxVal)
	{
		this.maxVal = maxVal;
	}

}
