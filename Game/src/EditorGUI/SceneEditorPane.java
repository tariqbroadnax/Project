package EditorGUI;

import static java.lang.Math.PI;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.InputMap;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import EditorActions.MoveCamera;
import Entity.Entity;
import EntityComponent.GraphicsComponent;
import Game.Scene;
import Graphic.Camera;
import Graphic.GraphicsContext;
import Graphic.TileMap2;
import Maths.Dimension2D;

public class SceneEditorPane extends JPanel	
	implements MouseListener, MouseMotionListener,
	ComponentListener, Observer
{	
	private GUIResources resources;
	
	private Scene scene;
	private TileMap2 tileMap;
	
	private Camera camera;
					
	private static final double MOVE_CAMERA_OFFSET = 5;
	
	private MoveCamera moveCameraUp,
					   moveCameraDown,
					   moveCameraLeft,
					   moveCameraRight;
	
	private boolean sceneContainsSelectedObject;
	
	private boolean mouseInside;
	
	private EntitySceneButtonManager buttonCreator;
	
	private MouseEventDispatcher mouseEventDispatcher;
	
	private Collection<SelectionTransferHandler> 
							STHandlers;
	
	private SelectionTransferHandler currSTHandler;
	
	public SceneEditorPane(GUIResources resources)
	{		
		this.resources = resources;
	
		scene  = resources.getScene();
		
		camera = new Camera();

		moveCameraUp = new MoveCamera(camera, 3*PI/2, 
									  MOVE_CAMERA_OFFSET);
		moveCameraDown = new MoveCamera(camera, PI/2, 
				  					  MOVE_CAMERA_OFFSET);
		moveCameraLeft = new MoveCamera(camera, PI, 
				  					  MOVE_CAMERA_OFFSET);
		moveCameraRight = new MoveCamera(camera, 2*PI, 
				  					  MOVE_CAMERA_OFFSET);
		
		buttonCreator = new EntitySceneButtonManager(
				resources, this);
		
		STHandlers = 
				new LinkedList<SelectionTransferHandler>();
		
		STHandlers.add(new TileTransferHandler(resources, this));
		
		sceneContainsSelectedObject = false;
		
		Observer cameraObserver = 
				(src, camera) -> repaint();
		
		camera.addObserver(cameraObserver);
		
		mouseEventDispatcher = new MouseEventDispatcher();
		
		resources.getSelectionManager()
			     .addObserver(this);

		addMouseListener(this);
		addMouseMotionListener(this);
		addComponentListener(this);
		
		addShortcuts();
		
		setLayout(null);
		
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		
		GraphicsContext gc = new GraphicsContext(
				(Graphics2D)g, camera,
				resources.getImagePool());
						
		scene.paint(gc);
		
		if(currSTHandler != null)
			currSTHandler.paintSelection(gc);
		
		gc.g2d.setTransform(new AffineTransform());
	}

	private void addShortcuts()
	{
		InputMap inputMap = getInputMap();
		ActionMap actionMap = getActionMap();
		
		inputMap.put(KeyStroke.getKeyStroke("UP"),
					 moveCameraUp);
		inputMap.put(KeyStroke.getKeyStroke("DOWN"),
				 	 moveCameraDown);
		inputMap.put(KeyStroke.getKeyStroke("LEFT"),
				 	 moveCameraLeft);
		inputMap.put(KeyStroke.getKeyStroke("RIGHT"),
				     moveCameraRight);
		
		Action[] actions = {
			moveCameraUp, moveCameraDown,
			moveCameraLeft, moveCameraRight
		};
		
		for(Action action : actions)
			actionMap.put(action, action);
	}
	
	private Point2D.Double cloneLocation(Entity entity)
	{
		Point2D.Double normalMouseLoc = 
				normalMouseLocation();

		Dimension2D.Double graphicSize = 
				entity.get(GraphicsComponent.class)
					  .getGraphic()
					  .getSize();
		
		return new Point2D.Double(
				normalMouseLoc.x - graphicSize.width/2,
				normalMouseLoc.y - graphicSize.height/2);
	}
	
	public Point2D.Double normalMouseLocation()
	{
		Point mouseLoc = getMousePosition();
				
		Point2D.Double normalLoc = 
				camera.normalLocation(mouseLoc);
				
		return normalLoc;
	}
	
	protected void addImpl(Component comp,
	           Object constraints,
	           int index)
	{
		super.addImpl(comp, constraints, index);
		
		//comp.addMouseListener(mouseEventDispatcher);
		comp.addMouseMotionListener(mouseEventDispatcher);
	
		/* Since children are deleted after removed, removing
		 * listener is not needed
		 */
	}
	
	private void updateSelectionTransferHandler()
	{
		List<Object> selectedObjs = 
				resources.getSelectionManager()
						 .getSelectedObjects();
		
		Iterator<SelectionTransferHandler> iter 
			= STHandlers.iterator();
	
		currSTHandler = null;
		
		while(iter.hasNext() && currSTHandler == null)
		{
			SelectionTransferHandler STHandler = 
					iter.next();
			
			if(STHandler.canImport(selectedObjs)) 
			{
				currSTHandler = STHandler;
				currSTHandler.setSelection(selectedObjs);
			}
		}		
	}
	
	public Camera getCamera()
	{
		return camera;
	}
	
	public boolean sceneContainsSelectedObject()
	{
		return sceneContainsSelectedObject;
	}
	
	Rectangle paintBound;
	
	public void mouseMoved(MouseEvent e)
	{
		repaint();
	}

	@Override
	public void mousePressed(MouseEvent e) 
	{		
		requestFocus();

		if(currSTHandler != null) 
		{
			currSTHandler.importSelection();
			repaint();
		}
	}
	
	
	@Override
	public void mouseDragged(MouseEvent e)
	{
		if(currSTHandler != null) 
		{
			currSTHandler.importSelection();
			repaint();
		}
	}
	
	@Override
	public void mouseEntered(MouseEvent e){
		mouseInside = true;
		repaint();
	}
	
	@Override
	public void mouseExited(MouseEvent e) 
	{
		if(!contains(e.getPoint()))
		{
			mouseInside = false;
			repaint();
		}		
	}

	@Override
	public void componentHidden(ComponentEvent arg0) {}

	@Override
	public void componentMoved(ComponentEvent arg0){}

	@Override
	public void componentResized(ComponentEvent arg0) {
		camera.setScreenSize(getSize());		
	}

	@Override
	public void componentShown(ComponentEvent e) {
		camera.setScreenSize(getSize());
	}


	@Override
	public void update(Observable o, Object arg) {
		updateSelectionTransferHandler();
	}
	
}
