package Editor.comp;

import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

import Editor.EditorResources;
import Editor.SceneListener;
import EditorGUI.MouseListener;
import EditorGUI.MouseMotionListener;
import Graphic.Camera;
import Tileset.TileMap;

public class TileMapComp extends JComponent
	implements SceneListener, MouseListener,
			   MouseMotionListener
{
	private EditorResources resources;
	private TileMap tm;
	private Camera camera;
	
	private boolean changedMouseCursor;
	
	private final int N = 1, S = 2, E = 4, W = 8,
					  NW = N|W, NE = N|E, SW = S|W, SE = S|E,
					  NO_BOUNDARY = 0;

	private int pressBoundary;
	private Point p;
	
	public TileMapComp(
			EditorResources resources,
			TileMap tm, Camera camera)
	{
		this.resources = resources;
		this.tm = tm;
		this.camera = camera;
		
		updateLocAndSize();
		
		resources.addSceneListener(this);
		addMouseMotionListener(this);
	}
	
	private void updateLocAndSize()
	{
		Rectangle2D.Double bound = tm.getBound();
		Rectangle scrBound = camera.onScreenBound2(bound);
		
		scrBound.x -= 5; scrBound.y -= 5;
		scrBound.width += 10; scrBound.height += 10;
		
		setBounds(scrBound);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
	}

	@Override
	public void sceneChanged() 
	{
		updateLocAndSize();
	}
	
	private int boundary(Point p)
	{
		Dimension size = getSize();
		
		int vertBound, horizBound;
		
		if(p.y < 10)
			vertBound = N;
		else if(p.y > size.height - 10)
			vertBound = S;
		else
			vertBound = NO_BOUNDARY;
		
		if(p.x < 10)
			horizBound = W;
		else if(p.x > size.width - 10)
			horizBound = E;
		else
			horizBound = NO_BOUNDARY;
		
		return vertBound | horizBound;
	}
	
	private void changeToSizeCursor(int boundary)
	{
		int type = -1;
		
		switch(boundary)
		{
			case N:
				type = Cursor.N_RESIZE_CURSOR;
				break;
			case S:
				type = Cursor.S_RESIZE_CURSOR;
				break;
			case E:
				type = Cursor.E_RESIZE_CURSOR;
				break;
			case W:
				type = Cursor.W_RESIZE_CURSOR;
				break;
			case NE:
				type = Cursor.NE_RESIZE_CURSOR;
				break;
			case NW:
				type = Cursor.NW_RESIZE_CURSOR;
				break;
			case SE:
				type = Cursor.SE_RESIZE_CURSOR;
				break;
			case SW:
				type = Cursor.SW_RESIZE_CURSOR;
				break;
			case NO_BOUNDARY:
				type = Cursor.DEFAULT_CURSOR;
				break;
		}
		
		Cursor cursor = new Cursor(type);
		
		setCursor(cursor);
	}
	
	private void updateSize()
	{
		
	}
	
	@Override
	public void mousePressed(MouseEvent e)
	{
		p = e.getPoint();
		pressBoundary = boundary(p);
	}
	
	@Override
	public void mouseMoved(MouseEvent e)
	{
		Point p = e.getPoint();
		int boundary = boundary(p);
		
		changeToSizeCursor(boundary);
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		p = e.getPoint();
	}
}
