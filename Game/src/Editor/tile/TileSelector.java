package Editor.tile;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JPanel;

import Editor.EditorResources;
import Editor.layer_selector.SetTiles;
import Editor.selection.SelectionHandler;
import EditorGUI.MouseListener;
import EditorGUI.MouseMotionListener;
import EditorGUI.UndoManager;
import Graphic.ImagePool;
import Tileset.TMCell;
import Tileset.Tile;
import Tileset.Tileset;

public class TileSelector extends JPanel
	implements MouseListener, MouseMotionListener,
	 		   FocusListener
{
	private EditorResources resources = Editor.Editor.RESOURCES;
	
	private Tileset tileset;
	
	private BufferedImage img;
	
	private boolean gridVisible;
	
	private double zoom;
	
	private Point hoverLoc;
			
	public TileSelector()
	{
		zoom = 0.75;
		
		gridVisible = true;
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addFocusListener(this);		
	}
	
	@Override
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		if(tileset == null) return;

		g = g.create();
		Graphics2D g2d = (Graphics2D)g;
		
		g2d.scale(zoom, zoom);

		g2d.drawImage(img, 0, 0, null);
		
		if(gridVisible)
			paintGridLines(g2d);
		
		checkAndPaintHoveredTile(g2d);
		checkAndPaintSelection(g2d);
		
		g2d.dispose();				
	}
	
	
	private void paintGridLines(Graphics g)
	{
		paintVertLines(g);
		paintHorizLines(g);
	}

	private void paintVertLines(Graphics g)
	{
		int y1 = 0, y2 = img.getHeight();
		
		int cols = tileset.getCols();
		
		int imgW = img.getWidth(),
			tileW = imgW / cols;

		g.setColor(Color.red);
		for(int i = 1; i < cols; i++)
		{
			int x = i * tileW;
			
			g.drawLine(x, y1, x, y2);
		}
	}
	
	private void paintHorizLines(Graphics g)
	{
		int x1 = 0, x2 = img.getWidth();
		
		int rows = tileset.getRows();
		
		int imgH = img.getHeight(),
			tileH = imgH / rows;
		
		g.setColor(Color.blue);
		for(int i = 1; i < rows; i++)
		{
			int y = i * tileH;
			
			g.drawLine(x1, y, x2, y);
		}
	}
	
	private void checkAndPaintSelection(Graphics g)
	{
		int rows = tileset.getRows(),
			cols = tileset.getCols();
		
		for(int row = 0; row < rows; row++)
		{
			for(int col = 0; col < cols; col++)
			{
				Tile tile = tileset.get(row, col);
				
				if(resources.getSelectionHandler()
							.onlySelection(tile))
					paintSelection(g, tile);
			}
		}
	}
	
	private void checkAndPaintHoveredTile(Graphics g)
	{
		if(hoverLoc == null) return;
		
		int x = (int)(hoverLoc.x / zoom),
			y = (int)(hoverLoc.y / zoom);
		
		if(tilesetContainsLoc(x, y))
			paintHoveredTile(g);
	}
	
	private void paintHoveredTile(Graphics g)
	{
		int rows = tileset.getRows(),
			cols = tileset.getCols();
				
		int imgW = img.getWidth(),
			tileW = imgW / cols;
	
		int imgH = img.getHeight(),
			tileH = imgH / rows;
		
		Color hoverColor = new Color(0, 0, 255, 120);
				
		g.setColor(hoverColor);
		g.fillRect(col((int)(hoverLoc.x / zoom)) * tileW,
				   row((int)(hoverLoc.y / zoom)) * tileH, tileW, tileH);
	}
	
	private void paintSelection(Graphics g, Tile tile)
	{
		int rows = tileset.getRows(),
			cols = tileset.getCols();
			
		int imgW = img.getWidth(),
			tileW = imgW / cols;

		int imgH = img.getHeight(),
			tileH = imgH / rows;
		
		int x = tile.col * tileW,
			y = tile.row * tileH;
	
		Color selectionColor = 
				new Color(0, 0, 255, 120);
		
		g.setColor(selectionColor);
		g.fillRect(x, y, tileW, tileH);
	}
	
	private boolean tilesetContainsLoc(int x, int y)
	{
		int rows = tileset.getRows(),
			cols = tileset.getCols();
		
		int row = row(y), col = col(x);

		return 0 <= row && row < rows &&
			   0 <= col && col < cols;
	}
	
	private Tile tileAtLoc(int x, int y)
	{
		int row = row(y), col = col(x);
		
		return tileset.get(row, col);
	}
	
	private int row(int y)
	{
		int rows = tileset.getRows();
	
		int imgH = img.getHeight(),
			tileH = imgH / rows;
			
		return y / tileH;
	}
	
	private int col(int x)
	{
		int cols = tileset.getCols();
		
		int imgW = img.getWidth(),
			tileW = imgW / cols;
			
		return x / tileW;
	
	}
	
	public void setTileset(Tileset tileset)
	{
		this.tileset = tileset;

		if(tileset != null)
		{
			File file = tileset.getFile();
		
			img = ImagePool.instance.getImage(file);			
		}
		else
			img = null;
		
		repaint();
	}
	
	public void setGridVisible(boolean gridVisible)
	{
		this.gridVisible = gridVisible;
		
		repaint();
	}
	
	public void setZoom(double zoom)
	{
		this.zoom = zoom;
		
		repaint();
	}
	
	public boolean isGridVisible() {
		return gridVisible;
	}
	
	public double getZoom() {
		return zoom;
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		requestFocus();
		
		if(tileset == null) return;
		
		int x = (int)(e.getX() / zoom),
			y = (int)(e.getY() / zoom);
		
		SelectionHandler handler = resources.getSelectionHandler();

		if(tilesetContainsLoc(x, y))
		{
			Tile tile = tileAtLoc(x, y);
			
			if(handler.onlySelection(tile))
			{
				handler.removeSelection();
				resources.setTool(resources.SELECT_TOOL);
			}
			else if(handler.instanceSelection(TMCell.class))
			{
				SetTiles edit = new SetTiles(resources, tile);
				UndoManager undoManager = resources.getUndoManager();
				
				edit.invoke();
				undoManager.addEdit(edit);
			}
			else
			{
				resources.setTool(resources.STAMP);
				handler.setSelection(tile, false);
			}
		}
		else
			handler.removeSelection();

		
		repaint();	
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		hoverLoc = e.getPoint();
		repaint();
	}
	
	@Override 
	public void mouseExited(MouseEvent e)
	{
		hoverLoc = null;
		repaint();
	}

	@Override
	public void focusGained(FocusEvent e) 
	{
		UndoManager undoManager = resources.getUndoManager();
		
		resources.getUndoAction()
				 .setUndoManager(undoManager);
		
		resources.getRedoAction()
				 .setUndoManager(undoManager);				
	}

	@Override
	public void focusLost(FocusEvent e) {}
}