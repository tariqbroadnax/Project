package Editor;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JPanel;

import Game.Scene;
import Graphic.Camera;
import Graphic.GraphicsContext;
import Maths.Vector2D;

public class SceneEditor extends JPanel
	implements MouseListener, MouseMotionListener,
			   KeyListener
{
	private EditorResources resources;
	
	private Scene scene;
	
	private Camera camera;
	
	private List<SelectionTransferHandler> STHs;

	private SelectionTransferHandler currSTH;
	
	private Point lastp, p;
	
	public SceneEditor(EditorResources resources)
	{
		this.resources = resources;
		
		scene = resources.scene;
		camera = new Camera();
		
		STHs = new LinkedList<SelectionTransferHandler>();
	
		STHs.add(new TileTransferHandler(resources));
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addKeyListener(this);
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		Dimension size = getSize();
		
		camera.setScreenDimension(size);
		
		GraphicsContext gc =
				new GraphicsContext((Graphics2D)g, camera);
	
		gc.setImagePool(resources.pool);
		
		camera.transformGraphics(gc.g2d);
		
		scene.paint(gc);
		
		checkAndPaintSelection(gc);
	}
	
	private void checkAndPaintSelection(GraphicsContext gc)
	{
		if(currSTH != null)
		{
			Point2D.Double normLoc = 
					camera.normalLocation(p);
		
			currSTH.paintSelection(gc, normLoc);
		}
	}
	
	private void importSelection()
	{
		Point2D.Double normLoc =
				camera.normalLocation(p);
		
		currSTH.importSelection(normLoc);
	}
	
	private void slide()
	{
		Vector2D.Double shift = camera.normalVector(
				lastp.x - p.x, lastp.y - p.y);

		camera.moveFocus(shift);
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		if(resources.selection == null)
			return;
		
		for(SelectionTransferHandler sth : STHs)
			if(sth.canImport(resources.selection))
			{	
				currSTH = sth;
				currSTH.setSelection(resources.selection);
				break;
			}
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		currSTH = null;
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		requestFocusInWindow();
		p = e.getPoint();

		if(currSTH != null)
		{	
			importSelection();
			repaint();	
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) 
	{
		
	}

	@Override
	public void mouseDragged(MouseEvent e) 
	{
		lastp = p;
		p = e.getPoint();
		
		if(currSTH == null)
			slide();
		else
			importSelection();
		
		repaint();
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		if(currSTH == null)
			return;
		
		p = e.getPoint();
		repaint();
	}

	@Override
	public void keyPressed(KeyEvent e) 
	{
		int kc = e.getKeyCode();
		
		switch(kc)
		{
			case KeyEvent.VK_UP:
				camera.moveFocus(0, -5);
				break;
			case KeyEvent.VK_DOWN:
				camera.moveFocus(0, 5);
				break;
			case KeyEvent.VK_LEFT:
				camera.moveFocus(-5, 0);
				break;
			case KeyEvent.VK_RIGHT:
				camera.moveFocus(5, 0);
				break;
		}
		
		repaint();
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}
