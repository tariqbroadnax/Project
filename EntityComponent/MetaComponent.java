package EntityComponent;

import java.time.Duration;

import Game.Entity;

public class MetaComponent extends EntityComponent
{
	private Entity creator;
	
	private boolean player = false;
	
	public MetaComponent()
	{
		super();
		
		creator = null;
		
		player = false;
	}
	
	public MetaComponent(MetaComponent comp)
	{
		this();
		
		creator = comp.creator;
	}
	
	@Override
	public void update(Duration delta){}
	
	public void setCreator(Entity creator)
	{
		this.creator = creator;
	}
	
	public Entity getCreator()
	{
		return creator;
	}
	
	public void setPlayer(boolean player)
	{
		this.player = player;
	}
	
	public boolean isPlayer()
	{
		return player;
	}
	
	@Override
	protected EntityComponent _clone()
	{
		return new MetaComponent(this);
	}
	
	public String toString()
	{
		return super.toString() +
			" creator: " + (creator == null ? "" : creator.toString2()) +
			" player: " + player;
	}
}
