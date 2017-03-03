package EntityComponent;

import java.time.Duration;

import Entity.Entity;

public class LifetimeComponent extends EntityComponent
{
	private Lifetime lifetime;
		
	public LifetimeComponent()
	{
		lifetime = new Lifetime();
	}
	
	public LifetimeComponent(LifetimeComponent comp)
	{
		lifetime = new Lifetime(comp.lifetime);
	}
	
	@Override
	public void update(Duration delta)
	{
		lifetime.update(delta);
		
		if(lifetime.isLifeOver())
		{
			Entity player = parent.getSceneLoc()
								  .getGame()
								  .getPlayer();
			
			if(parent != player)
			{
				parent.getSceneLoc()
					  .removeEntity(parent);
			}
		}
	}
	
	public void setLifetime(Lifetime lifetime) {
		this.lifetime = lifetime;
	}
	
	public Lifetime getLifetime() {
		return lifetime;
	}
	
	@Override
	protected EntityComponent _clone()
	{
		return new LifetimeComponent(this);
	}
	
	public String toString()
	{
		return super.toString() + "\n" + lifetime.toString();
	}
}
