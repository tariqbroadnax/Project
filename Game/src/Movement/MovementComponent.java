package Movement;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.time.Duration;
import java.util.LinkedList;
import java.util.List;

import Entity.Entity;
import EntityComponent.EntityComponent;

public class MovementComponent extends EntityComponent
{	
	protected Movement movement;

	private Cardinal dir, prevDir;
	
	private boolean moving;
	
	private transient List<MovementListener> listeners;

	private Point2D.Double prevLoc, parentLoc;
	
	public MovementComponent()
	{
		init();
	}
	
	private void init()
	{
		enabled = true;
		
		movement = new Movement();
		
		listeners = new LinkedList<MovementListener>();
		
		moving = false;
	
		prevLoc = new Point2D.Double();
		
		dir = prevDir = Cardinal.SOUTH;
	}
	
	public MovementComponent(MovementComponent comp)
	{
		this();
		
		movement = (Movement) comp.movement.clone();
	}
	
	@Override
	public void update(Duration delta)
	{				
		prevLoc.setLocation(parentLoc);

		movement.move(parentLoc, delta);
				
		parent.setLoc(parentLoc);
		
		notifyListeners();
	}
	
	private void notifyListeners()
	{
		if(!prevLoc.equals(parentLoc) && !moving)
		{
			moving = true;
			
			for(MovementListener listener : listeners)
				listener.movementStarted(this);
		}
		else if(prevLoc.equals(parentLoc) && moving)
		{
			moving = false;
						
			for(MovementListener listener : listeners)
				listener.movementStopped(this);
		}
				
		if(moving)
		{			
			prevDir = dir;
			
			double angle = Math.atan2(parentLoc.y - prevLoc.y,
									  parentLoc.x - prevLoc.x);
			
			dir = Cardinal.angleToCardinal(angle);
		
			for(MovementListener listener : listeners)
				listener.movementContinued(this);
		}
		if(!prevDir.equals(dir) && moving)
		{
			for(MovementListener listener : listeners)
				listener.directionChanged(this);
		}
	}
	
	public void addMovementListener(MovementListener listener)
	{
		listeners.add(listener);
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public Movement getMovement() {
		return movement;
	}
	
	public Cardinal getDirection() {
		return dir;
	}
	
	public Cardinal getPreviousDirection()
	{
		return prevDir;
	}
	
	public Point2D.Double getPrevLoc() {
		return prevLoc;
	}
	
	public boolean isMoving() {
		return moving;
	}
	
	@Override
	public void setParent(Entity parent)
	{
		super.setParent(parent);
		
		parentLoc = parent.getLoc();
	}
	
	@Override
	protected EntityComponent _clone()
	{
		return new MovementComponent(this);
	}
	
	private void readObject(java.io.ObjectInputStream in)
		     throws IOException, ClassNotFoundException
	{
		init();
		in.defaultReadObject();		
	}
}
