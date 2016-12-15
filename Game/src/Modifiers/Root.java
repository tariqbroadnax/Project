package Modifiers;

import java.time.Duration;

import Entity.Entity;
import EntityComponent.Lifetime;
import Movement.Movement;
import Movement.MovementComponent;

public class Root extends Effect
{
	private Lifetime lifetime;
	
	private Movement rootedMovement;
	
	public Root()
	{
		lifetime = new Lifetime(3000);
		rootedMovement = new Movement();
		
		rootedMovement.setSpeed(0);
	}
	
	public Root(Root root)
	{
		lifetime = new Lifetime(root.lifetime);
		rootedMovement = new Movement();
		
		rootedMovement.setSpeed(0);
	}
	
	public void start()
	{
		MovementComponent comp =
				target.get(MovementComponent.class);
		
		comp.setDisablingMovement(rootedMovement);
		comp.setEnabled(false);
	}
	
	public void stop()
	{
		MovementComponent comp =
				target.get(MovementComponent.class);
		
		comp.setEnabled(true);		
	}

	@Override
	public void update(Duration delta) {
		lifetime.update(delta);
	}

	@Override
	public boolean isFinished() {
		return lifetime.isLifeOver();
	}
	
	public void setLifetime(Lifetime lifetime) {
		this.lifetime = lifetime;
	}
	
	public void setLifetime(long lifetime) {
		this.lifetime = new Lifetime(lifetime);
	}

	@Override
	protected Effect _clone() {
		return new Root(this);
	}

	// FIXME
	@Override
	public boolean canBeApplied(Entity target) {
		// TODO Auto-generated method stub
		return false;
	}
}
