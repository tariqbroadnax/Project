package Item;

import CollisionResponses.ApplyModifiers;
import CollisionResponses.EndLifetime;
import EntityComponent.LifetimeComponent;
import Game.Entity;

public class InstantItem extends Item
{
	public InstantItem()
	{
		super();
		
		add(new LifetimeComponent());
	}
	
	public InstantItem(InstantItem item)
	{
		super(item);
	}
	
	@Override
	protected void _getPickedUp(Entity e)
	{
		ApplyModifiers.execute(this, e);
		EndLifetime.execute(this);	
	}

	@Override
	public Object _clone()
	{
		return new InstantItem(this);
	}
}
