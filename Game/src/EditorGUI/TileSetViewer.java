package EditorGUI;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

public class TileSetViewer extends ImageViewer
{
	private int rows = 1, cols = 1;

	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		drawImage(g);
		drawGrid(g);
	}
	
	private void drawGrid(Graphics g)
	{		
		if(getImage() == null)
			return;
		
		Dimension size = getImageScreenSize();
		
		Dimension cellSize = new Dimension(
						  size.width / cols,
						  size.height / rows);
		
		g.setColor(Color.black);
			
		drawVerticalLines(g, size.height, cellSize.width);
		drawHorizontalLines(g, size.width, cellSize.height);
	}
	
	private void drawHorizontalLines(
			Graphics g, int width, int cellHeight)
	{
		for(int r = 1; r < rows; r++)
		{
			int y = r * cellHeight;
			g.drawLine(0, y, width, y);
		}
	}
	
	private void drawVerticalLines(
			Graphics g, int height, int cellWidth)
	{
		for(int c = 1; c < cols; c++)
		{
			int x = c * cellWidth;
			g.drawLine(x, 0, x, height);
		}
	}
	
	public void setRows(int rows)
	{
		this.rows = rows < 1 ? this.rows : rows;
	}
	
	public void setColumns(int cols)
	{
		this.cols = cols < 1 ? this.cols : cols;
	}
	
	public void setRowsAndColumns(int rows, int cols)
	{
		setRows(rows);
		setColumns(cols);
		
		repaint();
	}
}
