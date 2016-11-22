package EntityComponent;

import java.io.IOException;
import java.time.Duration;

import Game.SceneResources;

public class LifetimeComponent extends EntityComponent
{
	private transient Lifetime lifetime;
		
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
	}
	
	public void checkAndRemoveParent(SceneResources resources)
	{		
		if(lifetime.isLifeOver())
			resources.removeQueue.add(parent);
	}
	
	public void setLifetime(Lifetime lifetime) {
		this.lifetime = lifetime;
	}
	
	public Lifetime getLifetime() {
		return lifetime;
	}
	
	private void readObject(java.io.ObjectInputStream in)
			throws IOException, ClassNotFoundException
	{
		in.defaultReadObject();
		lifetime = new Lifetime();
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
