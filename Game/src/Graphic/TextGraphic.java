package Graphic;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Rectangle2D.Double;

public class TextGraphic extends Graphic
{
	protected String text;

	private double charHeight, charWidth;
		
	private Paint paint;

	private String fontName;

	private int style;
	
	public TextGraphic()
	{
		text = "EMPTY";
		
		fontName = "COURIER";
		
		style = Font.PLAIN;
		
		paint = Color.RED;
		
		charHeight = 8;
		charWidth = 4;
	}
	
	public TextGraphic(TextGraphic graphic)
	{
		super(graphic);
		
		text = graphic.text;
	
		fontName = graphic.fontName;
		
		style = graphic.style;
		
		paint = graphic.paint;
		
		charHeight = graphic.charHeight;
		charWidth = graphic.charWidth;
	}
			
	public TextGraphic(String str) 
	{
		this();
		
		text = str;
	}

	protected void _paint(GraphicsContext gc)
	{
		Font font = new Font(fontName, style, 30);		
	
		FontMetrics metrics = gc.g2d.getFontMetrics(font);
				
		double scrW = gc.camera.screenWidth(charWidth * text.length()),
			   scrH = gc.camera.screenHeight(charHeight);
		
		int w = metrics.stringWidth(text),
			h = metrics.getMaxAscent() + 
			    metrics.getMaxDescent();
		
		double scaleX = scrW / w,
			   scaleY = 2 * scrH / h;
	
		Graphics2D g2d = (Graphics2D) gc.g2d.create();

		g2d.scale(scaleX, scaleY);

		Point2D.Double screenLoc = 
				gc.camera.screenLocation2D(
						loc.x - text.length()/2.0 * charWidth,
						loc.y + charWidth/2 + 1);
		
		screenLoc.x /= scaleX;
		screenLoc.y /= scaleY;

		g2d.setPaint(paint);
		g2d.setFont(font);
		g2d.drawString(text, (float)screenLoc.x, (float)screenLoc.y);	

		g2d.dispose();
	}

	public void setText(String text) {
		this.text = text;
	}
	
	public void setPaint(Paint paint) {
		this.paint = paint;
	}
	
	public void setCharHeight(double charHeight) {
		this.charHeight = charHeight;
	}
	
	public void setCharWidth(double charWidth) {
		this.charWidth = charWidth;
	}
	
	public void setFontName(String fontName) {
		this.fontName = fontName;
	}
	
	public void setFontStyle(int style) {
		this.style = style;
	}
	
	public String getText() {
		return text;
	}
	
	public Paint getPaint() {
		return paint;
	}
	
	public double getCharHeight() {
		return charHeight;
	}
	
	public double getCharWidth() {
		return charWidth;
	}
	
	public String getFontName() {
		return fontName;
	}
	
	public int getFontStyle() {
		return style;
	}

	@Override
	public Double getBound() 
	{
		return new Rectangle2D.Double(
				loc.x - text.length()/2.0 * charWidth,
				loc.y - charHeight/2,
				charWidth * text.length(),
				charHeight);
	}

	@Override
	public Object clone() {
		return new TextGraphic(this);
	}
}
