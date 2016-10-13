package Utilities;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class FontUtilities
{
	public static Font fontWithWidth(Font font, int width, String text, 
			Component comp)
	{
		int fontSize = 1;
		
		Font prevFont, newFont = null; 
		FontMetrics metrics;

		do {
			prevFont = newFont;
			
			newFont = new Font(
					font.getFontName(),
					font.getStyle(),
					fontSize);

			metrics = comp.getFontMetrics(newFont);
		
			fontSize++;	
			
		} while(metrics.stringWidth(text) < width);
		
		return prevFont;
	}
	
	public static Font clone(Font font)
	{
		return new Font(
				font.getFontName(),
				font.getStyle(),
				font.getSize());
	}
	
	public static Font fontWithHeight(Font font, int height,
			Graphics g)
	{
		int fontSize = 1;
		
		Font newFont;
		FontMetrics metrics;
		
		do
		{
			newFont = new Font(
					font.getFontName(),
					font.getStyle(),
					fontSize);
			
			metrics = g.getFontMetrics(newFont);
			
			fontSize++;
						
		} while(metrics.getHeight() < height);
		
		return newFont;
	}
	
	public static Font fontWithDimension(Font font, Dimension dim,
			String txt, Component comp)
	{
		int fontSize = 1;
		
		Font prevFont, newFont = null; 
		FontMetrics metrics;

		do {
			prevFont = newFont;
			
			newFont = new Font(
					font.getFontName(),
					font.getStyle(),
					fontSize);

			metrics = comp.getFontMetrics(newFont);
		
			fontSize++;	
			
		} while(metrics.stringWidth(txt) < dim.width &&
				metrics.getHeight() < dim.height);
		
		return prevFont;
	}
	
	public static Font fontWithDimension(Font font, Dimension dim,
			String txt, Graphics g)
	{
		int fontSize = 1;
		
		Font prevFont, newFont = null; 
		FontMetrics metrics;

		do {
			prevFont = newFont;
			
			newFont = new Font(
					font.getFontName(),
					font.getStyle(),
					fontSize);

			metrics = g.getFontMetrics(newFont);
		
			fontSize++;	
			
		} while(metrics.stringWidth(txt) < dim.width &&
				metrics.getHeight() < dim.height);
		
		return prevFont;
	}
	
}
