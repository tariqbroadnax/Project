package EditorGUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import EntityComponent.GraphicsComponent;
import Game.Entity;
import Game.Scene;
import Graphic.Camera;
import Graphic.Graphic;
import Graphic.GraphicsContext;
import Graphic.MouseMotionRepainter;
import Graphic.TileMap2;
import Graphic.Vector2D;
import Maths.Dimension2D;
import Maths.Direction2;

public class TileMapEditor extends JPanel
	implements MouseListener, MouseMotionListener
{	
	private GUIResources resources;
	
	private Scene scene;
	private TileMap2 tileMap;
	
	private Camera camera;
	
	private MousePositionListener mousePosListener;
	
	private boolean paintSelected;
	
	public TileMapEditor(GUIResources resources)
	{		
		this.resources = resources;
	
		SceneChangeListener l = src -> setTileMap();
		
		resources.addResourceListener(l);
		
		scene  = resources.getScene();
		
		tileMap = resources.getScene()
						   .getTileMap();
		
		camera = new Camera();
		
		for(Direction2 dir : Direction2.values())
		{
			addAction("moveCamera" + dir,
					  KeyStroke.getKeyStroke(dir.toString()),
					  e -> moveCamera(dir));
		}
		
		mousePosListener = new MousePositionListener();
		
		paintSelected = false;
		
		addMouseListener(this);
		
		addMouseMotionListener(mousePosListener);
		addMouseMotionListener(this);
		addMouseMotionListener(new MouseMotionRepainter(this));
		
		addComponentListener(camera);
	}
	
	private void addSelectedTileToMouseLoc()
	{
		ImageID id = (ImageID) resources.getSelectedObject();
		
		int index = tileIndexOfMouseLoc();
		
		tileMap.set(index, id.val);	
		
		repaint();
	}
	
	private void addSelectedEntityToMouseLoc()
	{
		Point2D.Double normalMouseLoc = 
				normalMouseLoc();
		
		Entity model = (Entity)
				resources.getSelectedObject(),
			   clone = new Entity(model);
		
		clone.setLoc(normalMouseLoc);
		
		resources.getScene()
				 .addEntity(clone);
		
		repaint();
	}
	
	private int tileIndexOfMouseLoc()
	{
		Point2D.Double normalMouseLoc = 
				normalMouseLoc();
				
		return tileMap.index(normalMouseLoc);
	}
	
	private Point2D.Double normalMouseLoc()
	{
		Point mouseLoc = 
				mousePosListener.getMouseLocation();
				
		Point2D.Double normalLoc = 
				camera.normalLocation(mouseLoc);
		
		return normalLoc;
	}

	private void addAction(
			String name, KeyStroke keyStroke, 
			ActionListener listener)
	{
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
		.put(keyStroke, name);
				
		getActionMap()
		.put(name, new AbstractAction() {
			public void actionPerformed(ActionEvent e) {
				listener.actionPerformed(e);
			}			
		});
	}
	
	public void moveCamera(Direction2 dir)
	{
		Vector2D.Double shift = null;
		switch(dir)
		{
			case UP: 
				shift = new Vector2D.Double(0, -2.5);
				break;
			case DOWN: 
				shift = new Vector2D.Double(0, 2.5);
				break;
			case LEFT:
				shift = new Vector2D.Double(-2.5, 0);
				break;
			case RIGHT:
				shift = new Vector2D.Double(2.5, 0);
				break;
		}
		
		Point2D.Double newFocus = 
				shift.getMoved(camera.getFocus());
		
		camera.setFocus(newFocus);
		
		repaint();
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
				
		GraphicsContext gc = new GraphicsContext(
				(Graphics2D)g, camera,
				resources.getImagePool());
					
		scene.paint(gc);
		
		if(paintSelected)
			paintSelectedObject(gc);
	}
	
	private void paintSelectedObject(GraphicsContext gc)
	{
		Object selectedObj = resources.getSelectedObject();

		if(selectedObj instanceof ImageID)
			paintHoveredTileArea(gc);
		else if(selectedObj instanceof Entity)
			paintSelectedEntityAtMouseLoc(gc);
	}
	
	private void paintHoveredTileArea(GraphicsContext gc)
	{
		Point mouseLoc = 
				mousePosListener.getMouseLocation();
		
		if(mouseLoc == null) return;
		
		Point2D.Double normalLoc = 
				camera.normalLocation(mouseLoc),
					   tileLoc = 
				tileMap.tileLocation(normalLoc),
					   screenLoc =
				camera.screenLocation(tileLoc);
						
		Dimension2D.Double screenDim = 
				camera.screenDimension(
						tileMap.getTileDimension());
		
		gc.g2d.setColor(Color.red);
		gc.g2d.draw(new Rectangle2D.Double(
				screenLoc.x, screenLoc.y,
				screenDim.width, screenDim.height));
	}
	
	private void paintSelectedEntityAtMouseLoc(
			GraphicsContext gc)
	{
		
		Entity entity = 
				(Entity)resources.getSelectedObject();
	
		if(entity.contains(GraphicsComponent.class))
		{
			GraphicsComponent comp =
					entity.get(GraphicsComponent.class);
			
			Graphic graphic = comp.getGraphic();
			
			Rectangle2D.Double bound = graphic.getBound();
			
			Point2D.Double normalMouseLoc = normalMouseLoc(),
						   paintLoc = new Point2D.Double(
								   normalMouseLoc.x - bound.width/2,
								   normalMouseLoc.y - bound.height/2);
			
			graphic.paint(gc, paintLoc);
		}
		else
			paintTransparentGridAtMouseLoc(gc.g2d);
	}
	
	private void setTileMap()
	{
		tileMap = resources.getScene()
				   .getTileMap();

		repaint();
	}
	
	private void paintTransparentGridAtMouseLoc(
			Graphics2D g2d)
	{
		AffineTransform transform = g2d.getTransform();
		
		g2d.setTransform(new AffineTransform());
		
		Point mouseLoc = 
				mousePosListener.getMouseLocation();
		
		Color color = Color.gray;
		
		for(int i = 0; i < 16; i++)
		{
			int col = i % 4,
				row = i / 4;
			
			g2d.setColor(color);
			
			g2d.fillRect(col * 10 + mouseLoc.x - 20,
						 row * 10 + mouseLoc.y - 20,
						 10, 10);
			
			if(i % 4 != 3)
				color = color == Color.gray ?
						Color.white : Color.gray;
		}
		
		g2d.setTransform(transform);
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{
		Object selectedObj = 
				resources.getSelectedObject();
		
		if(selectedObj instanceof ImageID)
			addSelectedTileToMouseLoc();
		
		else if(selectedObj instanceof Entity)
			addSelectedEntityToMouseLoc();
	}
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if(resources.getSelectedObject()
				instanceof ImageID)
			 addSelectedTileToMouseLoc();
	}
	
	@Override
	public void mouseEntered(MouseEvent e)
	{
		paintSelected = true;
	}
	
	@Override
	public void mouseExited(MouseEvent e)
	{
		paintSelected = false;
		repaint();
	}
}
