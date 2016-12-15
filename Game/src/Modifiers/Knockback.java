package Modifiers;

import java.time.Duration;

import Entity.Entity;
import EntityComponent.Lifetime;
import Maths.Maths;
import Movement.Movement;
import Movement.MovementComponent;

public class Knockback extends Effect
{
	private double speed;
	private Lifetime lifetime;
		
	public Knockback()
	{
		this(100, 100);
	}
	
	public Knockback(double speed, long lifetime)
	{		
		this.speed = speed;
		this.lifetime = new Lifetime(lifetime);
	}
	
	public Knockback(Knockback knockback)
	{
		speed = knockback.speed;
		lifetime = new Lifetime(knockback.lifetime);
	}
	
	@Override
	public void update(Duration delta) 
	{
		lifetime.update(delta);
	}
	
	public boolean canBeApplied(Entity target)
	{
		return target.contains(MovementComponent.class);
	}

	@Override
	public void start() 
	{
		Movement movement = new Movement();
		
		movement.setDirection(findDirection());
		movement.setSpeed(speed);
		movement.setEnabled(true);
		
		MovementComponent comp =
				target.get(MovementComponent.class);
		
		//System.out.println(target.getClass());
		
		comp.setDisablingMovement(movement);			
		comp.setEnabled(false);
	}

	@Override
	public void stop() {
		target.get(MovementComponent.class)
		  	  .setEnabled(true);
	}

	@Override
	public boolean isFinished() {
		return lifetime.isLifeOver();
	}
	
	private double findDirection() 
	{
		return Maths.angleFrom(src.getLoc(),
							   target.getLoc());
	}

	public void setSpeed(double speed)
	{
		this.speed = speed;
	}
	
	public void setLifetime(long lifetime)
	{
		this.lifetime = new Lifetime(lifetime);
	}
	
	@Override
	public Effect _clone()
	{
		return new Knockback(this);
	}

}
