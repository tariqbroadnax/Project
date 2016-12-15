package Graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

import Utilities.Fonts;

public class TextGraphic extends Graphic
{
	protected String text;

	private Font font;
	
	private int normHeight;
		
	private Paint paint;
	
	public TextGraphic()
	{
		text = "EMPTY";
		
		font = new Font("Consolas", Font.PLAIN, 12);
		
		paint = Color.RED;
		
		normHeight = 10;
	}
	
	public TextGraphic(TextGraphic graphic)
	{
		super(graphic);
		
		text = graphic.text;
	
		font = graphic.font;
		
		paint = graphic.paint;
		
		normHeight = graphic.normHeight;
	}
			
	protected void _paint(GraphicsContext gc)
	{
		double screenHeight = 
				gc.camera.screenHeight(normHeight);
		
		Font font = Fonts.fontWithHeight(
				this.font, (int) screenHeight, gc.g2d);
		
		Point2D.Double screenLoc = 
				gc.camera.screenLocation(loc);
		
		gc.g2d.setPaint(paint);
		gc.g2d.setFont(font);
		gc.g2d.drawString(text, (float)screenLoc.x, (float)screenLoc.y);		
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void setFont(Font font) {
		this.font = font;
	}
	
	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	
	@Override
	public Double getBound() 
	{
		return new Rectangle2D.Double(
				loc.x , loc.y - normHeight,
				normHeight * text.length(),
				normHeight);
	}

	@Override
	public Object clone() {
		return new TextGraphic(this);
	}
}
