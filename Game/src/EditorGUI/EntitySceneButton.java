package EditorGUI;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.dnd.DragSource;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;
import javax.swing.TransferHandler;

import Entity.Entity;
import EntityComponent.EntityComponent;
import EntityComponent.GraphicsComponent;
import Game.EntityListener;
import Graphic.Camera;
import Maths.Dimension2D;
import Maths.Vector2D;

public class EntitySceneButton extends JButton
	implements EntityListener, Observer,
			   ActionListener, MouseListener, 
			   MouseMotionListener
{
	// deletes button if they off screen
	// creates button if the on screen
	private GUIResources resources;
	private Camera camera;
	private Entity entity;
	
	private EntityTransferHandler transferHandler;
	
	public EntitySceneButton(
			GUIResources resources, 
			Camera camera, Entity entity)
	{
		this.resources = resources;
		this.camera = camera;
		this.entity = entity;
	
		transferHandler = 
				new EntityTransferHandler(
						entity, camera.getScreenSize());
		
		setOpaque(false);
		setContentAreaFilled(false);
		
		entity.addEntityListener(this);
		
		entity.get(GraphicsComponent.class)
			  .addObserver(this);
		
		camera.addObserver(this);
		
		addMouseListener(this);
		addMouseMotionListener(this);
		addActionListener(this);
		
		updateLocation();
		updateSize();	
		setTransferHandler(transferHandler);

		src = new DragSource();		
	//	src.createDefaultDragGestureRecognizer(this, DnDConstants.ACTION_COPY, this);
	}
	DragSource src;
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		//System.out.println(this.hashCode() + " " + isEnabled());
	}
	
	public void removeNotify()
	{
		super.removeNotify();
		
		entity.addEntityListener(this);
		
		entity.get(GraphicsComponent.class)
			  .deleteObserver(this);
	}
	
	private void updateLocation()
	{
		Point2D.Double loc = entity.getLoc(),
					   screenLoc = 
					   		 camera.screenLocation2(loc);
				
		Point currLoc = getLocation();
		Point newLoc = new Point(
				(int)Math.floor(screenLoc.x),
				(int)Math.floor(screenLoc.y));
	
		if(!newLoc.equals(currLoc))
			setLocation(newLoc);
	}
	
	private void updateSize()
	{
		Dimension2D.Double graphicSize =
				entity.get(GraphicsComponent.class)
					  .getGraphic()
					  .getSize();
		
		Dimension2D.Double graphicScreenSize =
				camera.sizeOnScreen(graphicSize);
		
		Dimension newSize = new Dimension(
				(int)Math.ceil(graphicScreenSize.width),
				(int)Math.ceil(graphicScreenSize.height));

		Dimension currSize = getSize();

		if(!currSize.equals(newSize))
			setSize(newSize);
	}

	private void updateObserver(
			GraphicsComponent oldComp,
			GraphicsComponent newComp) 
	{
		oldComp.deleteObserver(this);
		newComp.addObserver(this);
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		updateLocation();
		updateSize();
	}

	@Override
	public void componentAdded(
			Entity src, EntityComponent comp) {}

	@Override
	public void componentRemoved(
			Entity src, EntityComponent comp) {}

	@Override
	public void componentReplaced(
			Entity src, EntityComponent oldComp,
						EntityComponent newComp) 
	{
		if(newComp instanceof GraphicsComponent)
		{
			updateObserver((GraphicsComponent)oldComp,
						   (GraphicsComponent)newComp);	
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		resources.setSelectedObj(entity);
	}
	

	Point pressLoc;
	
	public void mousePressed(MouseEvent e)
	{
		//transferHandler.exportAsDrag(this, e, TransferHandler.MOVE);
		pressLoc = e.getPoint();
	}
	
	public void mouseMoved(MouseEvent e)
	{
		
	}
	
	public void mouseDragged(MouseEvent e) 
	{
		Point loc = 
				((Component)getParent()).getMousePosition();
		
		loc.x -= pressLoc.x;
		loc.y -= pressLoc.y;
				
		Point2D.Double normalLoc = 
				camera.normalLocation(loc);
		
		entity.setLoc(normalLoc);		
	}
}


