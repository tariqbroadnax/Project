package Movement;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import Entity.Entity;
import EntityComponent.EntityComponent;
import Modifiers.Modifier;

public class MovementComponent extends EntityComponent
{
	private boolean enabled;
	
	protected Movement normalMovement;

	private Movement disablingMovement;

	private Cardinal dir, prevDir;
	
	private boolean moving;
	
	private Collection<MovementListener> listeners;

	private Point2D.Double prevLoc,
						   parentLoc;

	private TreeSet<Modifier> speedMods;
	
	public MovementComponent()
	{
		init();
	}
	
	private void init()
	{
		enabled = true;
		
		normalMovement = new Movement();
		normalMovement.setSpeed(50);
		
		disablingMovement = new Movement();
		
		listeners = new LinkedList<MovementListener>();
		
		moving = false;
	
		prevLoc = new Point2D.Double();
		
		dir = prevDir = Cardinal.NORTH;
	
		speedMods = new TreeSet<Modifier>();
	}
	
	public MovementComponent(MovementComponent comp)
	{
		this();
		
		normalMovement = (Movement)comp.normalMovement.clone();
		disablingMovement = (Movement)comp.disablingMovement.clone();
	}
	
	@Override
	public void update(Duration delta)
	{		
		Movement movement;

		//System.out.println(enabled + " " + parent.getClass());
		movement = enabled ? normalMovement :
							 disablingMovement;
	
		prevLoc.setLocation(parentLoc);

		double prevSpeed = movement.getSpeed(),
			   currSpeed = prevSpeed;
			
		for(Modifier mod : speedMods)
			currSpeed = mod.modify(currSpeed);
		
		movement.setSpeed(currSpeed);
		movement.move(parentLoc, delta);
		movement.setSpeed(prevSpeed);
				
		notifyListeners();
	}
	
	private void notifyListeners()
	{
		if(!prevLoc.equals(parentLoc) &&
		   !moving)
		{
			moving = true;
			
			for(MovementListener listener : listeners)
				listener.movementStarted(this);
		}
		else if(prevLoc.equals(parentLoc) &&
				moving)
		{
			moving = false;
						
			for(MovementListener listener : listeners)
			{
				listener.movementStopped(this);
			}
		}
		
		if(moving)
		{
			prevDir = dir;
			
			double angle = Math.atan2(prevLoc.y - parentLoc.y,
									  prevLoc.x - parentLoc.x);
			
			dir = Cardinal.angleToCardinal(angle);
		}
		if(!prevDir.equals(dir) &&
			moving)
		{
			for(MovementListener listener : listeners)
				listener.directionChanged(this);
		}
	}
	
	public void addMovementListener(MovementListener listener)
	{
		listeners.add(listener);
	}
	
	public void addSpeedMod(Modifier mod)
	{
		speedMods.add(mod);
	}
	
	public void removeSpeedMod(Modifier mod)
	{
		speedMods.remove(mod);
	}
	
	public void setEnabled(boolean enabled)
	{
		this.enabled = enabled;
	}
	
	public void setNormalMovement(Movement normalMovement)
	{
		this.normalMovement = normalMovement;
	}
	
	public void setDisablingMovement(Movement disablingMovement)
	{
		this.disablingMovement = disablingMovement;
	}
	
	public Movement getDisablingMovement()
	{
		return disablingMovement;
	}
	
	public Movement getNormalMovement()
	{ 
		return normalMovement;
	}
	
	public Cardinal getDirection()
	{
		return dir;
	}
	
	public Cardinal getPreviousDirection()
	{
		return prevDir;
	}
	
	public boolean isMoving()
	{
		return moving;
	}
	
	public boolean isMovementEnabled()
	{
		return enabled;
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
