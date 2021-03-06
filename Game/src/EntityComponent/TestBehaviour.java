package EntityComponent;

import java.time.Duration;

import Behaviour.Behaviour;
import Entity.Entity;
import Movement.Force;
import Movement.MovementComponent;

public class TestBehaviour extends Behaviour
{
	private Force force;
	
	private long period = 5000,
				 elapsed = 0;
	
	private double radius = 50;

	public TestBehaviour()
	{
		force = new Force();
		
		force.setSpeed(2 * Math.PI * radius * 1000 / period);
	}
	
	@Override
	public void update(Duration delta) 
	{
		elapsed += delta.toMillis();
		elapsed %= period;
		
		double dir = 2 * Math.PI * elapsed / period;
		
		force.setDirection(dir);
	}
	
	public void setSrc(Entity src) 
	{
		super.setSrc(src);
		
		src.get(MovementComponent.class)
		   .getMovement()
		   .addForce(force);		
	}

}
