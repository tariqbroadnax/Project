package EntityComponent;

import java.time.Duration;

import Game.Entity;

public class Attacker 
{
	private Entity e;
	
	private Duration elapsedSinceAttack;
	
	public Attacker(Entity e)
	{
		this.e = e;
		
		elapsedSinceAttack = Duration.ZERO;
	}
	
	public void update(Duration delta)
	{
		elapsedSinceAttack = 
				elapsedSinceAttack.plus(delta);
	}
	
	public Duration getElapsedSinceAttack()
	{
		return elapsedSinceAttack;
	}
	
	public Entity getEntity()
	{
		return e;
	}
	
	public boolean equals(Object o)
	{
		return e == o;
	}
}
