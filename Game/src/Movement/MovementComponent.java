package Movement;

import java.awt.geom.Point2D;
import java.io.IOException;
import java.time.Duration;
import java.util.Collection;
import java.util.LinkedList;

import EntityComponent.EntityComponent;
import EntityComponent.StatsComponent;
import Game.Entity;
import Maths.Direction;
import Stat.CoreStatType;
import Stat.Stats;

public class MovementComponent extends EntityComponent
{
	private transient boolean enabled;
	
	protected Movement normalMovement;

	private Movement disablingMovement;

	private Direction dir, prevDir;
	
	private boolean moving;
	
	private transient Collection<MovementListener> listeners;

	private transient Point2D.Double prevLoc,
									 parentLoc;
	
	public MovementComponent()
	{
		init();
	}
	
	private void init()
	{
		enabled = true;
		
		normalMovement = new LooseMovement();
		disablingMovement = new LooseMovement();
		
		listeners = new LinkedList<MovementListener>();
		
		moving = false;
	
		prevLoc = new Point2D.Double();
		
		dir = prevDir = Direction.N;
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
		if(!parent.contains(StatsComponent.class)) return;
		
		Stats stats = parent.get(StatsComponent.class)
							.getStats();

		Movement movement;

		if(enabled)
		{
			movement = normalMovement;
			movement.setSpeed(stats.getValue(CoreStatType.SPEED));
		}
		else
			movement = disablingMovement;
			
		prevLoc.setLocation(parentLoc);
		
		movement.move(parentLoc, delta);
		
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
			dir = Direction.directionFrom(prevLoc, parentLoc);
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
	
	public Direction getDirection()
	{
		return dir;
	}
	
	public Direction getPreviousDirection()
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
