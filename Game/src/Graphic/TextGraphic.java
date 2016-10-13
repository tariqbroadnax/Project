package Graphic;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import Utilities.FontUtilities;

public class TextGraphic extends Graphic
{
	protected String text;

	private Font font;
	
	private int normalHeight;
		
	private Paint paint;
	
	public TextGraphic()
	{
		text = "EMPTY";
		
		font = new Font("Consolas", Font.PLAIN, 12);
		
		paint = Color.RED;
		
		normalHeight = 10;
	}
	
	public TextGraphic(TextGraphic graphic)
	{
		super(graphic);
		
		text = graphic.text;
	
		font = graphic.font;
		
		paint = graphic.paint;
		
		normalHeight = graphic.normalHeight;
	}
			
	protected void _paint(GraphicsContext gc)
	{
		double screenHeight = 
				normalHeight * gc.viewDim.height / 100;
		
		Font font = FontUtilities.fontWithHeight(
				this.font, (int) screenHeight, gc.g2d);
		
		Point2D.Double screenLoc = new Point2D.Double(
				loc.x * gc.viewDim.width / 100,
				loc.y * gc.viewDim.height / 100);
		
		gc.g2d.setPaint(paint);
		gc.g2d.setFont(font);
		gc.g2d.drawString(text, (float)screenLoc.x, (float)screenLoc.y);		
	}
	
	@Override
	public void paint(Point screenLoc, Dimension dim, Graphics2D g2d) 
	{
		Font font = FontUtilities.fontWithDimension(
				this.font, dim, text, g2d);
	
		g2d.setPaint(paint);
		g2d.setFont(font);
		g2d.drawString(text, (float)screenLoc.x, (float)screenLoc.y);		
	}

	public void setText(String text)
	{
		this.text = text;
	}
	
	public void setFont(Font font)
	{
		this.font = font;
	}
	
	public void setPaint(Paint paint)
	{
		this.paint = paint;
	}

	@Override
	protected Graphic _clone() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Double getBound() 
	{
		return new Rectangle2D.Double(
				loc.x , loc.y - normalHeight,
				normalHeight * text.length(),
				normalHeight);
	}
}
